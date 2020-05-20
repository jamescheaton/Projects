import pandas as pd
import numpy as np
from sklearn import preprocessing
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.linear_model import LinearRegression
from sklearn import linear_model, svm
from sklearn.metrics import mean_squared_error
from sklearn.ensemble import RandomForestRegressor
import math


# read in data
data_in = pd.read_csv('data/train.csv')


#Split into x and y
X = data_in.loc[:,'number_of_elements':'wtd_std_Valence']

# Previously used this to see how using a subset affects the output
#X = data_in[['wtd_std_ThermalConductivity', 'range_ThermalConductivity', 'std_ThermalConductivity', 'range_atomic_radius', 'wtd_entropy_atomic_mass', 'gmean_Density',
#'gmean_Valence', 'mean_Valence', 'wtd_gmean_Valence', 'wtd_mean_Valence']]
y = data_in[:]['critical_temp']

# Random splitting
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Remove garbage values
X_train = X_train.dropna(axis = 0, how = 'any')

#Standardise
X_train = preprocessing.scale(X_train)
X_test = preprocessing.scale(X_test)


def display_scores(scores):
    print("Scores:", scores)
    print("Mean:", scores.mean())
    print("Standard deviation:", scores.std())


# Run the different algorithms and display their scores
print("Ordinary Least Squares")
linreg = LinearRegression()
linreg.fit(X_train, y_train)
new_y = linreg.predict(X_train)
print("RMSE = ", math.sqrt(mean_squared_error(y_train, new_y)))
linreg_scores = cross_val_score(linreg, X_train, y_train, scoring='neg_mean_squared_error', cv = 10)
rmse_scores = np.sqrt(-linreg_scores)
display_scores(rmse_scores)

print("Elastic Net")
reg = linear_model.ElasticNet(random_state=0)
reg.fit(X_train, y_train)
new_y = reg.predict(X_train)
print("RMSE = ", math.sqrt(mean_squared_error(y_train, new_y)))
net_scores = cross_val_score(reg, X_train, y_train, scoring='neg_mean_squared_error', cv = 10)
rmse_scores = np.sqrt(-net_scores)
display_scores(rmse_scores)

print("Stochastic Gradient Descent")
clf = linear_model.SGDRegressor(max_iter=1000, tol=1e-3)
clf.fit(X_train, y_train)
new_y = clf.predict(X_train)
print("RMSE = ", math.sqrt(mean_squared_error(y_train, new_y)))
clf_scores = cross_val_score(clf, X_train, y_train, scoring='neg_mean_squared_error', cv = 10)
rmse_scores = np.sqrt(-clf_scores)
display_scores(rmse_scores)

print("Support Vector Machines")
clf = svm.SVR()
clf.fit(X_train, y_train)
new_y = clf.predict(X_train)
print("RMSE = ", math.sqrt(mean_squared_error(y_train, new_y)))
clf_scores = cross_val_score(clf, X_train, y_train, scoring='neg_mean_squared_error', cv = 10)
rmse_scores = np.sqrt(-clf_scores)
display_scores(rmse_scores)

## Commented this out as it takes ages to run

#print("Random Forest")
#regr = RandomForestRegressor(max_depth=2, random_state=0)
#regr.fit(X_train, y_train)
#new_y = regr.predict(X_train)
#print("RMSE = ", math.sqrt(mean_squared_error(y_train, new_y)))
#clf_scores = cross_val_score(regr, X_train, y_train, scoring='neg_mean_squared_error', cv = 10)
#rmse_scores = np.sqrt(-clf_scores)
#display_scores(rmse_scores)


# Test model on testing data
final_predict = clf.predict(X_test)
final_rmse = np.sqrt(mean_squared_error(y_test, final_predict))
print("Final RMSE = ", final_rmse)
