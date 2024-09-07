import PFR
import CSTR
import Batelada
import json
import os

if __name__ == '__main__':
    jsonPFR = os.path.join(os.path.dirname(__file__), 'inputPRF.json')
    jsonCSTR = os.path.join(os.path.dirname(__file__), 'inputCSTR.json')
    jsonBATE = os.path.join(os.path.dirname(__file__), 'inputBATE.json')

    if os.path.exists(jsonPFR):
        with open(jsonPFR, 'r') as f:
            dataPFR = json.load(f)
        PFR.process_PRF(dataPFR)

    if os.path.exists(jsonCSTR):
        with open(jsonCSTR, 'r') as f:
            dataCSTR = json.load(f)
        CSTR.process_CSTR(dataCSTR)

    if os.path.exists(jsonBATE):
        with open(jsonBATE, 'r') as f:
            dataBATE = json.load(f)
        Batelada.process_BATE(dataBATE)



