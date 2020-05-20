from sklearn.neural_network import MLPClassifier
from sklearn.externals import joblib
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import sys
from Basic import BasicAgent
from Intermediate import EarlyPrediction

# Read in arguments and choose appropriate agent
choice = sys.argv[1]

if choice == "Bas":
    agent = BasicAgent()
    agent.trainNetwork()
elif choice == "Int":
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
    # constantly ask user for input

    while True:
        ep = EarlyPrediction()
        encodedInput = ep.askUser()
        clf = joblib.load('mynetwork.joblib')

        np.reshape(encodedInput, (-1, 1))
        encodedInput = np.array([encodedInput])
        out = clf.predict(encodedInput)
        print(out)
        retrain = input("Is this the correct team? (Y/N)")
        # handle incorrect prediction
        if retrain == "N":
            encodedInput = pd.DataFrame(encodedInput)
            X.append(encodedInput)
            correct = input("What was the correct team?")
            correct = pd.Series([correct])
            Y.append(correct)
            print("Retraining network...")
            # retrain network
            clf = MLPClassifier(solver='sgd',
            learning_rate_init=0.1, hidden_layer_sizes=(1,7),
            verbose=False, momentum=0.03,
            activation='logistic',
            n_iter_no_change=10000, max_iter=100000)
            clf.fit(X,Y)
            joblib.dump(clf, 'mynetwork.joblib')








