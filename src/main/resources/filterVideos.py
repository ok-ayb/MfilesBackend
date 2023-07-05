import opennsfw2 as n2
import sys


# The video URL
def calculate_nsfw_probability(videoUrl):
    elapsed_seconds, nsfw_probabilities = n2.predict_video_frames(videoUrl)
    max_nsfw_probability = max(nsfw_probabilities)

    return max_nsfw_probability


videoUrl = sys.argv[1]
probability = calculate_nsfw_probability(videoUrl)
print(probability)
