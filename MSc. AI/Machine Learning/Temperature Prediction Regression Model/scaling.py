import pandas as pd
from sklearn.preprocessing import MinMaxScaler, StandardScaler
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt
import numpy as np

# Used to make the scaled plots in the report
data_in = pd.read_csv('data/train.csv')

X = data_in.loc[:,'number_of_elements':'wtd_std_Valence']
y = data_in[:]['critical_temp']

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
X_train = X_train.dropna(axis = 0, how = 'any')

X_plot = X_train[:]['wtd_mean_fie']
plt.scatter(X_plot, y_train, color='blue', alpha=.4, s=20, marker='o')
plt.xlabel("wtd_mean_fie before scaling")
plt.ylabel("Critical Temperature")
plt.show()
scaler = StandardScaler()
X_plot = np.array(X_plot)
X_plot = X_plot.reshape(-1,1)
scaler.fit(X_plot)
X_scale = scaler.transform(X_plot)
plt.scatter(X_scale, y_train, color='blue', alpha=.4, s=20, marker='o')
plt.xlabel("wtd_mean_fie after scaling")
plt.ylabel("Critical Temperature")
plt.show()
