from sklearn.neural_network import MLPClassifier
from sklearn.preprocessing import StandardScaler  
from sklearn.decomposition import PCA 
import pandas as pd

# Load the data file
def load_data(file_path):
    data = pd.read_csv(file_path, index_col=None)
    data = data.drop('Unnamed: 0', axis=1)
    data_clean = data.dropna(axis=0, how='any')
    X = data_clean.drop(['filenum', 'filename', 'classified_shape'], axis=1)
    scaler = StandardScaler()  
    scaler.fit(X)  
    X = scaler.transform(X)
    y = data_clean['classified_shape']
    return X, y

# Initialize the model
def initialize_model():
    model = MLPClassifier(activation='relu', alpha=0.0001, batch_size='auto', beta_1=0.9,
                          beta_2=0.999, early_stopping=False, epsilon=1e-08,
                          hidden_layer_sizes=(60, 100, 30, 100), learning_rate='constant',
                          learning_rate_init=0.01, max_iter=100, momentum=0.9,
                          nesterovs_momentum=True, power_t=0.5, random_state=525,
                          shuffle=True, solver='sgd', tol=0.0001, validation_fraction=0.1,
                          verbose=False, warm_start=False)
    return model

# Train the model with new data
def train_model(model, X_train, y_train):
    model.partial_fit(X_train, y_train, classes=np.unique(y_train))

# Evaluate the model
def evaluate_model(model, X_test, y_test):
    score = model.score(X_test, y_test)
    y_pred = model.predict(X_test)
    confusion_matrix = pd.crosstab(y_test, y_pred, margins=True)
    classification_rep = classification_report(y_test, y_pred)
    return score, confusion_matrix, classification_rep

# Main function
def main():
    # Load data
    X, y = load_data('all_features_female.csv')

    # Initialize model
    model = initialize_model()

    # Train the model
    model.fit(X, y)

    # Evaluate the model
    score, confusion_matrix, classification_rep = evaluate_model(model, X_test, Y_test)

    # Print results
    print("Model Accuracy:", score)
    print("\nConfusion Matrix:\n", confusion_matrix)
    print("\nClassification Report:\n", classification_rep)

if __name__ == "__main__":
    main()
