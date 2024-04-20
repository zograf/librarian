import json

main_subjects = ["fantasy", "politics", "horror", "thriller", "romance", "juvenile",
            "self-help", "sciencemathematcis", "biography", "psychology",
            "philosophy", "mystery", "magic", "literature", "poetry", "young"]

def get_all(file='./filtered_books_2.txt'):
    books = []
    with open(file, 'r') as f:
        for line in f.readlines():
            books.append(json.loads(line))
    return books


if __name__ == '__main__':
    books = get_all()

    total_subject_count = 0
    distinct_whole_subjects = {}


    for book in books:
        book_subjects = book['subjects']
        total_subject_count += len(book_subjects)
        if 'young' not in book_subjects: continue
        for subject in book_subjects:
            if subject in distinct_whole_subjects.keys():
                distinct_whole_subjects[subject] += 1
            else:  distinct_whole_subjects[subject] = 1

    print(f'total_subject_count: {total_subject_count}')
    print(f'distinct_whole_subjects: {len(distinct_whole_subjects.keys())}')

    with open('young_keywords.txt', 'w') as f:
        for subject, count in dict(sorted(distinct_whole_subjects.items(), key=lambda item: item[1], reverse=True)).items():
            f.write('{:5} | {}\n'.format(count, subject))