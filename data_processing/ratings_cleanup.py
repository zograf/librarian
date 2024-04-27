import json


def load_ratings(file='./ratings.txt'):
    ratings = []
    with open(file, 'r') as f:
        for line in f.readlines():
            ratings.append(json.loads(line))
    return ratings


if __name__ == '__main__':
    ratings = load_ratings()

    data = []
    for rating in ratings:
        data.append(json.dumps({'book_key' : rating['key'].split('/')[-1], 'rating' : rating['rating'], 'date' : rating['date']}) + '\n')

    with open('./ratings_mod.txt', 'w') as f:
        f.writelines(data)