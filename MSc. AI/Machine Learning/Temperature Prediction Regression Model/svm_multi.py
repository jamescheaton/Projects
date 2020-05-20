import pandas as pd
from sklearn import svm
from sklearn.feature_selection import SelectKBest, f_classif
from sklearn.model_selection import train_test_split
import numpy as np

X_in = pd.read_csv('../data/multi/X_train.csv', dtype='float64')
y_in = pd.read_csv('../data/multi/Y_train.csv')


for index, row in y_in.iterrows():
    if y_in.loc[index].at['whitecoat'] == 'background':
        y_in.loc[index].at['whitecoat'] = 0
    elif y_in.loc[index].at['whitecoat'] == 'whitecoat':
        y_in.loc[index].at['whitecoat'] = 1
    elif y_in.loc[index].at['whitecoat'] == 'juvenile':
        y_in.loc[index].at['whitecoat'] = 2
    elif y_in.loc[index].at['whitecoat'] == 'dead pup':
        y_in.loc[index].at['whitecoat'] = 3
    elif y_in.loc[index].at['whitecoat'] == 'moulted pup':
        y_in.loc[index].at['whitecoat'] = 4
    else:
        y_in.drop(index)
        X_in.drop(index)
y_in = y_in.astype('int')
X_test = pd.read_csv('../data/binary/X_test.csv', dtype='float64')

selector = SelectKBest(f_classif, k=100)
X_new = selector.fit_transform(X_in, y_in.values.ravel())


X_test = X_test.iloc[:, selector.get_support()]
clf = svm.SVC(kernel='linear', C=1)
clf.fit(X_new, y_in)
y_out = clf.predict(X_test)
y_out = pd.DataFrame(y_out)
y_print = pd.DataFrame(np.empty((y_out.shape[0], 1), dtype = np.str))
for index, row in y_out.iterrows():
    val = y_out.iat[index,0]
    if val == 0:
        y_print.iat[index, 0] = 'background'
    elif val == 1:
        y_print.iat[index, 0] = 'whitecoat'
    elif val == 2:
        y_print.iat[index, 0] = 'juvenile'
    elif val == 3:
        y_print.iat[index, 0] = 'dead pup'
    elif val == 4:
        y_print.iat[index, 0] = 'moulted pup'
print(y_print)
#y_print.to_csv('y_test_multi.csv', index=False)