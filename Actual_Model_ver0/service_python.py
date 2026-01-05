# process_image.py
import sys
from PIL import Image
import joblib
from jython_functions import main_function

def process_image(image_path):
    try:
        with Image.open(image_path) as img:
            # 이미지 처리 코드
            print(f"Processing image: {image_path}")
            img.show()  # 단순히 이미지를 보여줌 (예제)
    except Exception as e:
        print(f"Failed to process image: {e}")

#print(sys.argv.count)

while True:
    filepath = input().strip()
    if filepath == "exit":
        break
    dos = input().strip
    if dos == "exit":
        break
    length = input().strip()
    if length == "exit":
        break

    main_function(filepath, dos, length)