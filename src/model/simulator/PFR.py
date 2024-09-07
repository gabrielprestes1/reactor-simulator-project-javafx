import numpy as np
import cantera as ct
import matplotlib.pyplot as plt

def process_PRF(data):

    yaml_file = data['yamlFile']
    T0 = float(data['initialTemperature'])
    pressure = float(data['initialPressure'])
    composition_0 = data['composition']
    D = float(data['diameter'])
    length = float(data['length'])
    u0 = float(data['inflowVelocity'])
    energyChoice = int(data['energyChoice'])

    # Carregar a solução do arquivo YAML
    gas = ct.Solution(yaml_file)
    gas.TPX = T0, pressure, composition_0
    Ac = np.pi * D**2 / 4

    # Criar o reator de fluxo
    reactor = ct.FlowReactor(gas)
    reactor.area = Ac
    reactor.mass_flow_rate = gas.density * u0 * Ac

    if energyChoice == 1:
        reactor.energy_enabled = True
    else:
        reactor.energy_enabled = False

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


    # Plot the results
    plt.rcParams['figure.constrained_layout.use'] = True
    f, ax = plt.subplots(2, 2, figsize=(11, 8))

    # plot the temperature profile along the flow direction
    ax_t = ax[0, 0].plot(soln.x, soln.T[:], color='C3',  label='temperature')
    ax[0, 0].set(xlabel='Distance (m)', ylabel='Temperature (K)')

    # plot the pressure of the gas along the flow direction
    ax_p = ax[0, 0].twinx()
    h_p = ax_p.plot(soln.x, soln.P, color='C2', label='pressure')
    ax_p.set(ylabel='Pressure (Pa)')
    ax_p.legend(handles=ax_t+h_p, loc='best')

    # plot gas velocity along the flow direction
    h_vel = ax[0, 1].plot(soln.x, soln.speed, color='C0', label='velocity')
    ax[0, 1].set(xlabel='Distance (m)', ylabel='Velocity (m/s)')

    # plot gas density along the flow direction
    ax_rho = ax[0, 1].twinx()
    h_rho = ax_rho.plot(soln.x, soln.density * 1000, color='C1', label='density')
    ax_rho.set(ylabel=r'Density ($\mathregular{g/cm^3}$)')
    ax_rho.legend(handles=h_vel+h_rho, loc='best')

    minor_idx = []
    major_idx = []
    for i, name in enumerate(gas.species_names):
        mean = np.mean(soln(name).Y)
        if mean >= 0.01:
            major_idx.append(i)
            minor_idx.append(i)

    for j in major_idx:
        ax[1, 0].plot(soln.x, soln.Y[:,j], label=gas.species_name(j))
    ax[1, 0].legend(fontsize=8, loc='best')
    ax[1, 0].set(xlabel='Distance (m)', ylabel='Mass Fraction')

    for j in minor_idx:
        ax[1, 1].plot(soln.x, soln.X[:,j], label=gas.species_name(j))
    ax[1, 1].legend(fontsize=8, loc='best')
    ax[1, 1].set(xlabel='Distance (m)', ylabel='Mole Fraction')

    plt.savefig('src/model/simulator/results.png')


