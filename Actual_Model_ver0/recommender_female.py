import matplotlib.pyplot as plt
from PIL import Image, ImageDraw, ImageFont
import pandas as pd
import numpy as np
import pathlib
import os
import random
import uuid

# 사용할 데이터프레임 생성
style_df = pd.DataFrame(columns=['face_shape', 'hair_length', 'location', 'filename', 'score'])

# 스타일 데이터 처리 함수
def process_rec_pics_female(style_df, image_dir="data/pics_female"):
    image_root = "data/rec_pics_female"
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

# 사용자에게 스타일을 추천하고 입력을 받는 함수
def run_recommender_female(test_shape):
    name = input("What is your name? ")
    print("Hello, %s." % name)
    face_shape_input = test_shape
    if face_shape_input not in ['heart', 'long', 'oval', 'round', 'square']:
        print(test_shape)
        face_shape_input = input("What is your face shape? ")
    updo_input = input("Would you like to see up-dos? (Y/N) ")
    if updo_input.lower() in ['n', 'no']:
        hair_length_input = input("Is your hair short (shoulder-length shorter) or long? ")
        hair_length_input = 'Short' if hair_length_input.lower() in ['short', 's'] else 'Long'
    else:
        hair_length_input = 'Updo'

    r = 6
    n_col = 3
    n_row = 2

    recommended_df = style_df.loc[(style_df['face_shape'] == face_shape_input) & (style_df['hair_length'] == hair_length_input)].sort_values('score', ascending=0).reset_index(drop=True)
    recommended_df = recommended_df.head(r)

    plt.figure(figsize=(5 * n_col, 4 * n_row))
    plt.subplots_adjust(bottom=0, left=.01, right=.99, top=.90, hspace=.35)

    # Arial 대신 Nirmala 폰트 사용
    font_path = 'C:\\Windows\\Fonts\\Nirmala.ttf'
    font = ImageFont.truetype(font_path, 60)

    for p in range(0, r):
        idea = str(recommended_df.iloc[p]['location'])
        idea = idea.replace('\\', '/')
        print('idea : ', idea)
        img = Image.open(idea)
        plt.subplot(n_row, n_col, p + 1)
        draw = ImageDraw.Draw(img)
        plt.title(p + 1, fontsize=40)
        plt.xlabel(recommended_df.iloc[p]['score'], fontsize=20)
        plt.xticks([])
        plt.yticks([])
        plt.imshow(img)
        img.close()

    plt.show()

    fav = input("Which style is your favorite? ")
    yuck = input("Which style is your least favorite? ")

    for row in range(0, r):
        fn = recommended_df.at[row, 'filename']
        srow = style_df.index[style_df['filename'] == fn].tolist()
        srow = srow[0]
        if str(row + 1) == fav:
            style_df.at[srow, 'score'] += 5
        if str(row + 1) == yuck:
            style_df.at[srow, 'score'] -= 5

# 얼굴 형태별로 추천을 받아 이미지 파일을 생성하는 함수
def run_recommender_face_shape_female(test_shape, hair_length_input):
    face_shape_input = test_shape
    r = 6
    n_col = 3
    n_row = 2
    img_path = []
    recommended_df = style_df.loc[(style_df['face_shape'] == face_shape_input) & (style_df['hair_length'] == hair_length_input)].sort_values('score', ascending=0).reset_index(drop=True)
    recommended_df = recommended_df.head(r)

    plt.figure(figsize=(4 * n_col, 3 * n_row))
    plt.subplots_adjust(bottom=.06, left=.01, right=.99, top=.90, hspace=.50)

    # Arial 대신 Nirmala 폰트 사용
    font_path = 'C:\\Windows\\Fonts\\Nirmala.ttf'
    font = ImageFont.truetype(font_path, 60)

    for p in range(0, r):
        idea = str(recommended_df.iloc[p]['location'])
        idea = idea.replace('\\', '/')
        img = Image.open(idea)
        plt.subplot(n_row, n_col, p + 1)
        img_path.append(idea)
        draw = ImageDraw.Draw(img)
        plt.title(p + 1, fontsize=40)
        plt.xlabel(recommended_df.iloc[p]['score'], fontsize=20)
        plt.xticks([])
        plt.yticks([])
        plt.imshow(img)
        img.close()

    img_id = uuid.uuid4()
    img_filename = f"output/output_{img_id}.png"
    plt.savefig(img_filename)
    return img_filename

