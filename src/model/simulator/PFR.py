import numpy as np
import cantera as ct
import matplotlib.pyplot as plt

def process_PRF(yaml_file, composition_0, u0, length, D, T0, pressure, energyChoice, number):

    # Carregar a solução do arquivo YAML
    gas = ct.Solution(yaml_file)
    gas.TPX = float(T0), float(pressure), composition_0
    Ac = np.pi * float(D)**2 / 4

    # Criar o reator de fluxo
    reactor = ct.FlowReactor(gas)
    reactor.area = Ac
    reactor.mass_flow_rate = gas.density * float(u0) * Ac

    if float(energyChoice) == 1:
        reactor.energy_enabled = True
    else:
        reactor.energy_enabled = False

    # Criar os reservatórios
    upstream = ct.Reservoir(gas, name='upstream')
    downstream = ct.Reservoir(gas, name='downstream')

    # Criar controladores de fluxo e pressão
    m = ct.MassFlowController(upstream, reactor, mdot=gas.density * float(u0) * Ac)
    v = ct.PressureController(reactor, downstream, primary=m, K=1e-5)

    # Criar a rede de reatores
    net = ct.ReactorNet([reactor])
    soln = ct.SolutionArray(gas, extra=['x', 'speed'])

    # Simular o processo
    while net.distance < float(length):
        net.step()
        soln.append(TDY=reactor.thermo.TDY,
                    x=net.distance,
                    speed=reactor.speed)

    # Plotar os resultados
    plt.rcParams['figure.constrained_layout.use'] = True
    f, ax = plt.subplots(2, 2, figsize=(11, 8))
    f.suptitle(f'PFR {number} Simulation Results', fontsize=28)

    # Perfil de temperatura
    ax_t = ax[0, 0].plot(soln.x, soln.T[:], color='C3',  label='temperature')
    ax[0, 0].set(xlabel='Distance (m)', ylabel='Temperature (K)')

    # Pressão ao longo da direção do fluxo
    ax_p = ax[0, 0].twinx()
    h_p = ax_p.plot(soln.x, soln.P, color='C2', label='pressure')
    ax_p.set(ylabel='Pressure (Pa)')
    ax_p.legend(handles=ax_t+h_p, loc='best')

    # Velocidade do gás ao longo da direção do fluxo
    h_vel = ax[0, 1].plot(soln.x, soln.speed, color='C0', label='velocity')
    ax[0, 1].set(xlabel='Distance (m)', ylabel='Velocity (m/s)')

    # Densidade do gás ao longo da direção do fluxo
    ax_rho = ax[0, 1].twinx()
    h_rho = ax_rho.plot(soln.x, soln.density * 1000, color='C1', label='density')
    ax_rho.set(ylabel=r'Density ($\mathregular{g/cm^3}$)')
    ax_rho.legend(handles=h_vel+h_rho, loc='best')

    # Frações de massa
    major_idx = []
    minor_idx = []
    for i, name in enumerate(gas.species_names):
        mean = np.mean(soln(name).Y)
        if mean >= 0.01:
            major_idx.append(i)
            minor_idx.append(i)

    for j in major_idx:
        ax[1, 0].plot(soln.x, soln.Y[:,j], label=gas.species_name(j))
    ax[1, 0].legend(fontsize=8, loc='best')
    ax[1, 0].set(xlabel='Distance (m)', ylabel='Mass Fraction')

    mole_fractions = {}
    for j in minor_idx:
        ax[1, 1].plot(soln.x, soln.X[:,j], label=gas.species_name(j))
        mole_fractions[gas.species_name(j)] = float(soln.X[:,j][-1])  # Convertendo para float
    ax[1, 1].legend(fontsize=8, loc='best')
    ax[1, 1].set(xlabel='Distance (m)', ylabel='Mole Fraction')

    plt.savefig('src/model/simulator/results_REAC' + str(number) + '.png')

    # Retornar os resultados
    return yaml_file, float(soln.T[-1]), float(soln.P[-1]), mole_fractions


def mole_fractions_to_string(mole_fractions):
    composition_str = ', '.join([f"{species}:{value}" for species, value in mole_fractions.items()])
    return composition_str



