import json


def get_all(file='./filtered_books_4.txt'):
    books = []
    with open(file, 'r') as f:
        for line in f.readlines():
            books.append(json.loads(line))
    return books


if __name__ == '__main__':
    books = get_all()
    keys = {}

    for book in books:
        keys[book['key']] = 0

    ratings = []
    distinct = set()

    with open('./ratings.txt', 'r') as f:
        for line in f.readlines():
            params = line.split('\t')
            rating = {
                'key': params[0].strip(),
                'rating': int(params[-2].strip()),
                'date': params[-1].strip()
            }
            try:
                if keys[rating['key']] == 0:
                    ratings.append(rating)
                    distinct.add(rating['key'])
            except Exception:
                continue

    print(len(distinct))

    # with open('./ratings.txt', 'w') as f:
    #     for rating in ratings:
    #         f.write(json.dumps(rating) + '\n')
