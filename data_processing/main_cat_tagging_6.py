import json
import re

main_subjects = ["fantasy", "fiction", "politics", "horror", "thriller", "romance", "juvenile",
            "self-help", "science", "mathematics", "biography", "psychology",
            "philosophy", "mystery", "magic", "literature", "poetry", "young", "history"]

def load_books(file='./filtered_books_5.txt'):
    books = []
    with open(file, 'r') as f:
        for line in f.readlines():
            books.append(json.loads(line))
    return books


if __name__ == '__main__':
    books = load_books()
    print(f'Loaded {len(books)} books')
    data = []
    final_main_set = set()

    for book in books:
        book_subjects = book['subjects']
        if 'nonfiction' in book_subjects: continue
        main_detected = False

        for subject in book_subjects:
            for main in main_subjects:
                if main in subject:
                    main_detected = True
                    book['category'] = main
                    if book['category'] == 'fantasy': book['category'] = 'fiction'
                    data.append(json.dumps(book) + '\n')
                    final_main_set.add(main)
                    break
            if main_detected:
                break

    print(final_main_set)

    with open('./filtered_books_6.txt', 'w') as f:
        f.writelines(data)