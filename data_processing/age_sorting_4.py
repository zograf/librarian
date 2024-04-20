import json

main_subjects = ["fantasy", "politics", "horror", "thriller", "romance", "juvenile",
            "self-help", "sciencemathematcis", "biography", "psychology",
            "philosophy", "mystery", "magic", "literature", "poetry", "young"]

def get_all(file='./filtered_books_3.txt'):
    books = []
    with open(file, 'r') as f:
        for line in f.readlines():
            books.append(json.loads(line))
    return books


def write_all(data):
    with open('./filtered_books_4.txt', 'w') as f:
        f.writelines(data)


if __name__ == '__main__':
    books = get_all()

    total_subject_count = 0
    distinct_whole_subjects = {}

    data = []

    for book in books:
        book_subjects = book['subjects']
        age_group = 2
        if 'juvenile' in book_subjects or 'juvenile fiction' in book_subjects: age_group = 0
        for subject in book_subjects:
            if 'young adult' in subject:
                age_group = 1
                break
        book['age_group'] = age_group
        data.append(json.dumps(book) + '\n')

    write_all(data)