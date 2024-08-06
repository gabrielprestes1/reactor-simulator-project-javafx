import numpy as np
import cantera as ct
import json
import pandas as pd

def process_PRF(data):
    yaml_file = data['yamlFile']
    T0 = float(data['initialTemperature'])
    pressure = float(data['initialPressure'])
    composition_0 = data['composition']
    D = float(data['diameter'])
    length = float(data['length'])
    u0 = float(data['inflowVelocity'])

    # Carregar a solução do arquivo YAML
    gas = ct.Solution(yaml_file)
    gas.TPX = T0, pressure, composition_0
    Ac = np.pi * D**2 / 4

    # Criar o reator de fluxo
    reactor = ct.FlowReactor(gas)
    reactor.area = Ac
    reactor.mass_flow_rate = gas.density * u0 * Ac
    reactor.energy_enabled = True

    # Criar os reservatórios
    upstream = ct.Reservoir(gas, name='upstream')
    downstream = ct.Reservoir(gas, name='downstream')

    # Criar controladores de fluxo e pressão
    m = ct.MassFlowController(upstream, reactor, mdot=gas.density * u0 * Ac)
    v = ct.PressureController(reactor, downstream, primary=m, K=1e-5)

    # Criar a rede de reatores
    net = ct.ReactorNet([reactor])
    soln = ct.SolutionArray(gas, extra=['x', 'speed'])

    # Simular o processo
    while net.distance < length:
        net.step()
        soln.append(TDY=reactor.thermo.TDY,
                    x=net.distance,
                    speed=reactor.speed)

    # Coletar dados para saída
    mass_idx = []
    mole_idx = []
    mass_labels = []
    mole_labels = []

    for i, name in enumerate(gas.species_names):
        mean = np.mean(soln(name).Y)
        if mean >= 0.01:
            mass_idx.append(i)
            mole_idx.append(i)
            if mean >= 0.01:  # Usar a mesma condição para rótulos de massa e mole
                mass_labels.append(gas.species_name(i))
                mole_labels.append(gas.species_name(i))

    # Coletar dados de frações
    mass_data = [soln.Y[:, j] for j in mass_idx]
    mole_data = [soln.Y[:, j] for j in mole_idx]

    distance = soln.x
    temperature = soln.T
    pressure = soln.P
    speed = soln.speed
    density = soln.density * 1000  # kg/m^3

    # Criar DataFrame com dados básicos
    df = pd.DataFrame({
        'Distance': distance,
        'Temperature': temperature,
        'Pressure': pressure,
        'Speed': speed,
        'Density (kg/m^3)': density
    })

    # Adicionar frações de massa
    for i, label in enumerate(mass_labels):
        df[f'Mass Fraction ({label})'] = mass_data[i]

    # Adicionar frações molares
    for i, label in enumerate(mole_labels):
        df[f'Mole Fraction ({label})'] = mole_data[i]

    # Salvar DataFrame em um arquivo CSV
    df.to_csv('C:/Users/Gabri/project-tcc/reactor-simulator-project-javafx/src/model/simulator/Dados.csv', index=False)

if __name__ == "__main__":
    json_file_path = "C:/Users/Gabri/project-tcc/reactor-simulator-project-javafx/src/model/simulator/input.json"

    with open(json_file_path, 'r') as f:
        data = json.load(f)

    process_PRF(data)
