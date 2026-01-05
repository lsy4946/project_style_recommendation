# process_image.py
import sys
from PIL import Image
import joblib

def process_image(image_path):
    try:
        with Image.open(image_path) as img:
            # 이미지 처리 코드
            print(f"Processing image: {image_path}")
            img.show()  # 단순히 이미지를 보여줌 (예제)
    except Exception as e:
        print(f"Failed to process image: {e}")

def main():
    image_path = sys.argv[1]
    dos = sys.argv[2]
    length = sys.argv[3]
    process_image(image_path)

if __name__ == "__main__":
    main()