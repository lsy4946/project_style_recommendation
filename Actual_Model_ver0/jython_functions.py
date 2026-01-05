import matplotlib.pyplot as plt
from PIL import Image, ImageDraw, ImageFont
import pandas as pd
import numpy as np
import pathlib
import os
import random
import uuid
import joblib
from functions_only_save import make_face_df_save
from dataframe import df

style_df = pd.DataFrame(columns=['face_shape', 'hair_length', 'location', 'filename', 'score'])

def process_rec_pics_female(style_df, image_dir="data/pics_female"):
    image_root = "C:/study/spring/demo/Actual_Model_ver0/data/rec_pics_female"
    dir_list = ['heart', 'long', 'oval', 'square', 'round']
    filenum = 0
    for dd in dir_list:
        image_dir = image_root + '/' + dd
        sub_dir = [q for q in pathlib.Path(image_dir).iterdir() if q.is_dir()]
        for j in sub_dir:
            for p in j.iterdir():
                shape_array = []
                face_shape = os.path.basename(os.path.dirname(os.path.dirname(p)))
                hair_length = os.path.basename(os.path.dirname(p))
                sub_dir_file = p
                face_file_name = os.path.basename(p)
                shape_array.extend([face_shape, hair_length, sub_dir_file, face_file_name])
                random.seed(filenum)
                rand = random.randint(25, 75)
                shape_array.append(rand)
                style_df.loc[filenum] = np.array(shape_array)
                filenum += 1
    return filenum

def run_recommender_female(test_shape, hair_length_input, updo_input):
    face_shape_input = test_shape
    if updo_input.lower() == 'yes':
        hair_length_input = 'Updo'

    r = 6
    n_col = 3
    n_row = 2

    recommended_df = style_df.loc[(style_df['face_shape'] == face_shape_input) & (style_df['hair_length'] == hair_length_input)].sort_values('score', ascending=0).reset_index(drop=True)
    recommended_df = recommended_df.head(r)

    plt.figure(figsize=(5 * n_col, 4 * n_row))
    plt.subplots_adjust(bottom=0, left=.01, right=.99, top=.90, hspace=.35)

    #print(recommended_df.head(1))
    i=0
    for path in recommended_df['location']:
        print(path)


def main_function(imagePath, person_see_up_dos, person_hair_length):
    file_num = 0
    make_face_df_save(imagePath, file_num, df)
    dfc = df
    test_row = dfc.loc[file_num].values.reshape(1, -1)
    loaded_model = joblib.load('C:/study/spring/demo/Actual_Model_ver0/best_mlp_model_female.joblib')
    predictions = loaded_model.predict(test_row)
    process_rec_pics_female(style_df, image_dir=imagePath)
    run_recommender_female(predictions[0], person_hair_length, person_see_up_dos)