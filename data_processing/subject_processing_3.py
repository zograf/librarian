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


def write_all(data):
    with open('./filtered_books_3.txt', 'w') as f:
        f.writelines(data)


if __name__ == '__main__':
    keep_subjects_threshold = 20
    books = get_all()

    total_subject_count = 0
    distinct_whole_subjects = {}

    for book in books:
        book_subjects = book['subjects']
        total_subject_count += len(book_subjects)
        for subject in book_subjects:
            if subject in distinct_whole_subjects.keys():
                distinct_whole_subjects[subject] += 1
            else:  distinct_whole_subjects[subject] = 1

    subjects_sorted = dict(sorted(distinct_whole_subjects.items(), key=lambda item: item[1], reverse=True))
    subjects_keep = [k for k, v in subjects_sorted.items() if v >= keep_subjects_threshold]
    # with open('keywords_keep.txt', 'w') as f:
    #     for subject, count in subjects_sorted.items():
    #         f.write('{:5} | {}\n'.format(count, subject))

    data = []
    for book in books:
        subjects = book['subjects']
        keep_subjects = []
        for subject in subjects:
            if subject in subjects_keep:
                keep_subjects.append(subject)
        book['subjects'] = keep_subjects
        if len(keep_subjects) >= 5:
            data.append(json.dumps(book) + '\n')

    print('Writing..')

    write_all(data)
