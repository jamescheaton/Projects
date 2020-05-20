import pandas as pd
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt


# Plots all the features against critical temperature
data_in = pd.read_csv('data/train.csv')

X = data_in.loc[:,'number_of_elements':'wtd_std_Valence']
y = data_in[:]['critical_temp']

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
X_train = X_train.dropna(axis = 0, how = 'any')


columns = X_train.columns
fig, axs = plt.subplots(11, 8)
for i in range(0,11):
    for j in range(0,8):
        if i * j > 80:
            continue # issie with this as it will still plot the top row over and over again
        col = columns[i * j]
        X_plot = X_train[:][col]
        axs[i, j].scatter(X_plot, y_train, color='blue', alpha=.4, s=20, marker='o')

plt.show()
