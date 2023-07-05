import opennsfw2 as n2
import sys
import requests
from io import BytesIO


def calculate_nsfw_probability(imageUrl):
    response = requests.get(imageUrl)
    image = BytesIO(response.content)
    nsfw_probability = n2.predict_image(image)

    return nsfw_probability


imageUrl = sys.argv[1]

probability = calculate_nsfw_probability(imageUrl)
print(probability)
