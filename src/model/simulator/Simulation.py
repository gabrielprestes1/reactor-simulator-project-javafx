import json
import os
import re
import PFR
import CSTR
import Batelada


json_file = os.path.join(os.path.dirname(__file__), 'input.json')

if os.path.exists(json_file):
    with open(json_file, 'r') as f:
        data = json.load(f)

    arquivo, temperatura_final, pressao_final, fracoes_moleculares = None, None, None, None


    n = 0
    for reactor, values in data.items():
        n += 1
        reactor_type = re.sub(r'_\d+', '', reactor)
        reactor_id = reactor.split('_')[1]

        if reactor_type == "PFR":

            if n > 1:
                values = [
                    arquivo,
                    fracoes_moleculares,
                    values[2],
                    values[3],
                    values[4],
                    pressao_final,
                    temperatura_final,
                    values[7]
                ]


            arquivo, temperatura_final, pressao_final, fracoes_moleculares = PFR.process_PRF(*values, n)

        elif reactor_type == "CSTR":

            if n > 1:
                values = [
                    arquivo,
                    fracoes_moleculares,
                    values[2],
                    values[3],
                    values[4],
                    pressao_final,
                    temperatura_final,
                    values[7]
                ]

            arquivo, temperatura_final, pressao_final, fracoes_moleculares = CSTR.process_CSTR(*values, n)

        elif reactor_type == "BATE":
            Batelada.process_BATE(*values, n)


        data[reactor] = values


