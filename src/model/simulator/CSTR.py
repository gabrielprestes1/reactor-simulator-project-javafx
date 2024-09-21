import numpy as np
import cantera as ct
import matplotlib.pyplot as plt

def process_CSTR(yaml_file, composition_0, V, flow, total_time, T0, pressure, energyChoice, number):

    # Carregar a solução do arquivo YAML
    gas = ct.Solution(yaml_file)
    gas.TPX = float(T0), float(pressure), composition_0

    # Criar o reator CSTR
    reactor = ct.Reactor(gas)
    reactor.volume = float(V)

    if float(energyChoice) == 1:
        reactor.energy_enabled = True
    else:
        reactor.energy_enabled = False

    # Criar reservatórios para entrada e saída
    upstream = ct.Reservoir(gas, name='upstream')
    downstream = ct.Reservoir(gas, name='downstream')

    # Controlador de fluxo
    m = ct.MassFlowController(upstream, reactor, mdot= gas.density * float(flow) )

    # Criar um controlador de pressão
    v = ct.PressureController(reactor, downstream, primary=m, K=1e-3)

    # Criar a rede de reatores
    net = ct.ReactorNet([reactor])
    soln = ct.SolutionArray(gas, extra=['time'])

    t = 0
    dt = 0.1
    while t < float(total_time):
        t += dt
        net.advance(t)
        soln.append(reactor.thermo.state, time=t)

    # Plotar os resultados
    plt.rcParams['figure.constrained_layout.use'] = True
    f, ax = plt.subplots(2, 2, figsize=(11, 8))
    f.suptitle(f'CSTR {number} Simulation Results', fontsize=28)

    # Plotar a temperatura do reator
    ax[0, 0].plot(soln.time, soln.T, color='C3', label='Temperature')
    ax[0, 0].set(xlabel='Time (s)', ylabel='Temperature (K)')
    ax[0, 0].legend(loc='best')

    ax[0, 1].plot(soln.time, soln.P, color='C2', label='Pressure')
    ax[0, 1].set(xlabel='Time (s)', ylabel='Pressure (Pa)')
    ax[0, 1].legend(loc='best')

    minor_idx = []
    major_idx = []
    for i, name in enumerate(gas.species_names):
        mean = np.mean(soln(name).Y)
        if mean >= 0.01:
            major_idx.append(i)
            minor_idx.append(i)

    for j in major_idx:
        ax[1, 0].plot(soln.time, soln.Y[:,j], label=gas.species_name(j))
    ax[1, 0].legend(fontsize=8, loc='best')
    ax[1, 0].set(xlabel='Time (s)', ylabel='Mass Fraction')

    mole_fractions = {}
    for j in minor_idx:
        ax[1, 1].plot(soln.time, soln.X[:,j], label=gas.species_name(j))
        mole_fractions[gas.species_name(j)] = float(soln.X[:,j][-1])
    ax[1, 1].legend(fontsize=8, loc='best')
    ax[1, 1].set(xlabel='Time (s)', ylabel='Mole Fraction')

    plt.savefig('src/model/simulator/results_REAC' + str(number) + '.png')

    return yaml_file, float(soln.T[-1]), float(soln.P[-1]), mole_fractions

def mole_fractions_to_string(mole_fractions):
    composition_str = ', '.join([f"{species}:{value}" for species, value in mole_fractions.items()])
    return composition_str

