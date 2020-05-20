from sklearn.neural_network import MLPClassifier
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from sklearn.externals import joblib

class BasicAgent:
    def __init__(self):
        super().__init__()

    def trainNetwork(self):
        # read in csv file and encode appropriately
        df=pd.read_csv('../tickets.csv', header=None, dtype='str')

        shape = df.shape
        row = shape[0]
        col = shape[1]
        print(col)
        print(row)
        Y = df.loc[1:250,9]
        for i in range(1,row):
            for j in range(col):
                val = df.loc[i].iat[j]
                if val == 'Yes':
                    df.loc[i].iat[j] = 1
                if val == 'No':
                    df.loc[i].iat[j] = 0

        print(df)

        # format training data correctly
        X = df.loc[1:250,0:8]

        # initialise network with chosen parameters
        clf = MLPClassifier(solver='sgd',
        learning_rate_init=0.1, hidden_layer_sizes=(1,7),
        verbose=False, momentum=0.05,
        activation='logistic',
        n_iter_no_change=10000, max_iter=50000)
        clf.fit(X,Y)
        # save network to file
        joblib.dump(clf, 'mynetwork.joblib')
        
