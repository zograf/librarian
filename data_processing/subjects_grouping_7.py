import json
import re

main_subjects = ['literature', 'poetry', 'juvenile', 'young', 'psychology', 'fantasy', 'politics', 'fiction', 'romance', 'biography', 'philosophy', 'science', 'magic', 'thriller', 'horror', 'history', 'mystery', 'mathematics']

def load_books(file='./filtered_books_6.txt'):
    books = []
    with open(file, 'r') as f:
        for line in f.readlines():
            books.append(json.loads(line))
    return books


def write_report(subject_per_main):
    with open('subjects_per_main.txt', 'w') as f:
        for subject, counts in subject_per_main.items():
            total = 0
            max = 0
            max_key = ""
            for cat, count in counts.items():
                if count > max:
                    max = count
                    max_key = cat
                total += count

            f.writelines('{:55} | {:20} | {}%\n'.format(subject, f'{max_key} ({max})', int(max / total * 100)))


def write_subjects(subject_per_main):
    data = []
    for subject, counts in subject_per_main.items():
        max, max_key = 0, ""
        for cat, count in counts.items():
            if count > max:
                max, max_key = count, cat
        data.append(json.dumps({'parent': max_key, 'keyword': subject }) + '\n')
    with open('subjects.txt', 'w') as f:
        f.writelines(data)


if __name__ == '__main__':
    books = load_books()
    print(f'Loaded {len(books)} books')

    subject_per_main = {}

    for book in books:
        book_subjects = book['subjects']
        main = book['category']

        for subject in book_subjects:
            if subject not in subject_per_main:
                subject_per_main[subject] = {main : 1}
            else:
                if main not in subject_per_main[subject]:
                    subject_per_main[subject][main] = 1
                else:
                    subject_per_main[subject][main] += 1
            break

    write_subjects(subject_per_main)