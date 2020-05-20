import pandas as pd
from sklearn import svm
from sklearn.feature_selection import SelectKBest, f_classif
from sklearn.model_selection import train_test_split
import numpy as np
from sklearn.neural_network import MLPClassifier
from sklearn.metrics import accuracy_score
from sklearn.metrics import recall_score
from sklearn.metrics import balanced_accuracy_score

X_in = pd.read_csv('../data/binary/X_train.csv', dtype='float64')
y_in = pd.read_csv('../data/binary/Y_train.csv')

for index, row in y_in.iterrows():
    if y_in.loc[index].at['background'] == 'background':
        y_in.loc[index].at['background'] = 0
    elif y_in.loc[index].at['background'] == 'whitecoat':
        y_in.loc[index].at['background'] = 1
    else:
        y_in.drop(index)
        X_in.drop(index)
y_in = y_in.astype('int')
X_test = pd.read_csv('../data/binary/X_test.csv', dtype='float64')

selector = SelectKBest(f_classif, k=100)
X_new = selector.fit_transform(X_in, y_in.values.ravel())


X_test = X_test.iloc[:, selector.get_support()]

clf = MLPClassifier(activation='logistic', solver='sgd', alpha=1e-5, hidden_layer_sizes=(100, ), random_state=1)

clf.fit(X_new, y_in)
y_acc = clf.predict(X_new)
y_out = clf.predict(X_test)
y_out = pd.DataFrame(y_out)
y_print = pd.DataFrame(np.empty((y_out.shape[0], 1), dtype = np.str))
for index, row in y_out.iterrows():
    val = y_out.iat[index,0]
    if val == 0:
        y_print.iat[index, 0] = 'background'
    elif val == 1:
        y_print.iat[index, 0] = 'seal'

acc = accuracy_score(y_in, y_acc)
print(acc)
print(balanced_accuracy_score(y_in, y_acc))
print(recall_score(y_in, y_acc, average=None))