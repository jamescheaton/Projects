import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split

# Used to show the correlations in the report
data_in = pd.read_csv('data/train.csv')
train, test = train_test_split(data_in, test_size=0.2, random_state=42)
corr_matrix = train.corr()
with pd.option_context('display.max_rows', None, 'display.max_columns', None):
    print(corr_matrix["critical_temp"].sort_values(ascending=False))
