from typing import Union
import pytesseract
from PIL import Image
import pyttsx3		
from happytransformer import HappyTextToText, TTSettings
from fastapi import FastAPI, Request
from io import BytesIO
import base64
from typing import Any, Dict, AnyStr, List, Union


JSONObject = Dict[AnyStr, Any]
JSONArray = List[Any]
JSONStructure = Union[JSONArray, JSONObject]



app = FastAPI()


@app.post("/")
def read_root(arbitrary_json: JSONStructure = None):
    
    decodeit = open('img.png', 'wb')
    decodeit.write(base64.b64decode((arbitrary_json[b'base64_file'])))
    decodeit.close()

    img = Image.open('img3.png')
    pytesseract.pytesseract.tesseract_cmd ='C:/Program Files (x86)/Tesseract-OCR/tesseract.exe'
    # converts the image to result and saves it into result variable
    result: str = pytesseract.image_to_string(img)

    happy_tt = HappyTextToText("T5", "vennify/t5-base-grammar-correction")
    args = TTSettings(num_beams=5, min_length=1, max_length=10000)
    # result1 = happy_tt.generate_text(f"grammar: {result}", args=args).text

    f_s = result.find("Tessemct")
    f_e = f_s + len("Tessemct")
    s_s = result.find("Ocmd")
    s_e = s_s + len("Ocmd")
    return {"text": result, "index": [{"start": f_s, "end": f_e}, {"start": s_s, "end": s_e}]}