from joblib import dump, load

# 객체 저장
dump(obj, 'filename.joblib')

# 객체 불러오기
obj = load('filename.joblib')

def predict():
    