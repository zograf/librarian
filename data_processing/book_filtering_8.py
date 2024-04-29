import json

def main():
    #with open("../filtered_books_6.txt", "r") as f:
    #    books = [json.loads(line) for line in f.readlines()]

    #book_map = {}
    #for book in books:
    #    if book['key'] in book_map:
    #        print(f"OVO JE ISSUE: {book['key']}")
    #    else:
    #        book_map[book['key']] = 0

    with open("../filtered_books_6.txt", "r") as f:
        books = [json.loads(line) for line in f.readlines()]

    with open("../subjects.txt", "r") as f:
        subjects = [json.loads(line) for line in f.readlines()]

    found = False
    counter = 0
    for book in books:
        keep = []
        for subject in book["subjects"]:
            found = False
            for sub in subjects:
                if sub["keyword"] == subject:
                    found = True
                    keep.append(subject)
                    break
        book["subjects"] = keep
        counter += 1
        if (counter % 10000 == 0):
            print(f"{counter}/{len(books)}")
        
    removed = 0
    with open("filtered_books_7.txt", "w") as f:
        for book in books:
            if (len(book['subjects']) == 0):
                removed += 1 
            else:
                f.write(json.dumps(book) + "\n")
    print(removed)

if __name__ == "__main__":
    main()