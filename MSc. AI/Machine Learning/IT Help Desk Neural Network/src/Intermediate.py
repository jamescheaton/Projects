import pandas as pd
import numpy as np
from scipy import stats
from sklearn.externals import joblib
from sklearn.neural_network import MLPClassifier

class EarlyPrediction:
    # read in csv in case we have to append to it later
    def __init__(self):
        super().__init__()
        df=pd.read_csv('../tickets.csv', header=None, dtype='str')
        shape = df.shape
        row = shape[0]
        col = shape[1]
        Y = df.loc[1:250,9]
        for i in range(1,row):
            for j in range(col):
                val = df.loc[i].iat[j]
                if val == 'Yes':
                    df.loc[i].iat[j] = 1
                if val == 'No':
                    df.loc[i].iat[j] = 0
        X = df.loc[1:250,0:8]

    def askUser(self):
        print("Welcome to the IT help-desk.")
        print("Please answer the questions below")
        request = input("Is this a request? (Y/N)")
        inputVals = np.array([request])
        incident = input("Is this an incident? (Y/N)")
        inputVals = np.append(inputVals, incident)
        webServices = input("Is this a Web Services issue? (Y/N)")
        inputVals = np.append(inputVals, webServices)
        login = input("Is this a login issue? (Y/N)")
        inputVals = np.append(inputVals, login)
        early = input("Would you liked to make an early prediction? (Y/N)")
        if early == "Y":
            retarr = self.fillMode(inputVals, 5)
            return retarr
        wireless = input("Is this a wireless issue? (Y/N)")
        inputVals = np.append(inputVals, wireless)
        early = input("Would you liked to make an early prediction? (Y/N)")
        if early == "Y":
            retarr = self.fillMode(inputVals, 4)
            return retarr
        printing = input("Is this a printing issue? (Y/N)")
        inputVals = np.append(inputVals, printing)
        early = input("Would you liked to make an early prediction? (Y/N)")
        if early == "Y":
            retarr = self.fillMode(inputVals, 3)
            return retarr
        idCards = input("Is this an ID card issue? (Y/N)")
        inputVals = np.append(inputVals, idCards)
        early = input("Would you liked to make an early prediction? (Y/N)")
        if early == "Y":
            retarr = self.fillMode(inputVals, 2)
            return retarr
        staff = input("Is this a staff issue? (Y/N)")
        inputVals = np.append(inputVals, staff)
        early = input("Would you liked to make an early prediction? (Y/N)")
        if early == "Y":
            retarr = self.fillMode(inputVals, 1)
            return retarr
        students = input("Is this a student issue? (Y/N)")
        inputVals = np.append(inputVals, students)
        userInput = np.array([request, incident, webServices, login, wireless, printing, idCards, staff, students])
        for ticket in userInput :
            if ticket == "Y":
                ticket = 1
            elif ticket == "N":
                ticket = 0
            encondedInput = np.append(encondedInput, ticket)
        print(encondedInput)
        return encondedInput

    # if incomplete data, fill with the mode
    def fillMode(self, arr, num_vals):
        encoded = np.array([])
        for ticket in arr:
            if ticket == "Y":
                ticket = 1
            elif ticket == "N":
                ticket = 0
            encoded = np.append(encoded, ticket)
        inputMode = stats.mode(encoded)
        for x in range(0, num_vals):    
            encoded = np.append(encoded, inputMode[0])
        return encoded



