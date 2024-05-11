import json

BOOK_PATH = "./filtered_books_7.txt"
AUTHOR_PATH = "./authors_filtered.txt"
SUBJECT_PATH = "./filtered_subjects.txt"
RATING_PATH = "./ratings.txt"

BOOK_ID = 1
AUTHOR_ID = 1
SUBJECT_ID = 1
RATING_ID = 1

book_map = {}
book_id_map = {}
author_map = {}
author_id_map = {}
subject_map = {}
subject_id_map = {}
rating_map = {}
rating_id_map = {}

def load_books():
    global BOOK_PATH, BOOK_ID, book_map, book_id_map
    with open(BOOK_PATH, "r", encoding="utf-8") as f:
        for line in f.readlines():
            book = json.loads(line)
            book_map[book['key']] = BOOK_ID
            book_id_map[BOOK_ID] = book
            BOOK_ID += 1

def load_authors():
    global AUTHOR_PATH, AUTHOR_ID, author_map, author_id_map
    with open(AUTHOR_PATH, "r", encoding="utf-8") as f:
        for line in f.readlines():
            author = json.loads(line)
            author_map[author['key']] = AUTHOR_ID
            author_id_map[AUTHOR_ID] = author
            AUTHOR_ID += 1

def load_subjects():
    global SUBJECT_PATH, SUBJECT_ID, subject_map, subject_id_map
    with open(SUBJECT_PATH, "r", encoding="utf-8") as f:
        for line in f.readlines():
            subject = json.loads(line)
            subject_map[subject['keyword']] = SUBJECT_ID
            subject_id_map[SUBJECT_ID] = subject
            SUBJECT_ID += 1

def load_ratings():
    global RATING_PATH, RATING_ID, rating_map, rating_id_map
    with open(RATING_PATH, "r", encoding="utf-8") as f:
        for line in f.readlines():
            rating = json.loads(line)
            rating_map[rating['key']] = RATING_ID
            rating_id_map[RATING_ID] = rating
            RATING_ID += 1

def main():
    global BOOK_PATH, AUTHOR_PATH, SUBJECT_PATH, RATING_PATH
    global BOOK_ID, AUTHOR_ID, SUBJECT_ID, RATING_ID
    global book_map, book_id_map, author_map, author_id_map
    global subject_map, subject_id_map, rating_map, rating_id_map

    # LOAD
    load_books()
    load_authors()
    load_subjects()
    load_ratings()

    #with open("../data.sql", "w", encoding="utf-8") as f:

    # INSERT ENTITIES

    with open("./sql/author.csv", "w", encoding="utf-8") as f:
        for id in author_id_map:
            author = author_id_map[id] 
            f.write(str(id) + "," + author['key'] + "," + author['name'].replace(",", "") + "\n")

    with open("./sql/subject.csv", "w", encoding="utf-8") as f:
        for id in subject_id_map:
            subject = subject_id_map[id] 
            f.write(str(id) + "," + subject['keyword'] + "," + subject['parent'] + "," + str(subject['relevance']) + "\n")

    with open("./sql/book.csv", "w", encoding="utf-8") as f:
        for id in book_id_map:
            book = book_id_map[id] 
            category_id = subject_map[book['category']]
            raw_description = book['description'].replace("\n", "\\n").replace("\r", "\\r").replace(",", "")
            raw_title = book['title'].replace("\n", "\\n").replace("\r", "\\r").replace(",", "")
            raw_subtitle = book['subtitle'].replace("\n", "\\n").replace("\r", "\\r").replace(",", "")
            raw_first_sentence = book['first_sentence'].replace("\n", "\\n").replace("\r", "\\r").replace(",", "")

            f.write(str(id) + "," + str(book['age_group']) + "," + book['cover'] + 
                    "," + raw_description[:245] + "," + str(book['first_published_year']) + 
                    "," + raw_first_sentence[:245] + "," + book['key'] + "," + raw_subtitle[:245] + 
                    "," + raw_title[:245] + "," + str(category_id) + "\n")

    # INSERT JOINED

    with open("./sql/rating.csv", "w", encoding="utf-8") as f:
        for id in rating_id_map:
            rating = rating_id_map[id] 
            if (rating['key'] not in book_map):
                continue
            book_id = book_map[rating['key']]
            f.write(str(id) + "," + str(rating['date']) + "," + str(rating['rating']) + "," + str(book_id) + "\n")

    with open("./sql/book_subjects.csv", "w", encoding="utf-8") as f:
        for book_id in book_id_map:
            book = book_id_map[id]
            for s in book['subjects']:
                subject_id = subject_map[s]
                f.write(str(book_id) + "," + str(subject_id) + "\n")

    with open("./sql/book_authors.csv", "w", encoding="utf-8") as f:
        for book_id in book_id_map:
            book = book_id_map[id]
            for a in book['authors']:
                author_id = author_map[a]
                f.write(str(book_id) + "," + str(author_id) + "\n")
        

if __name__ == "__main__":
    main()