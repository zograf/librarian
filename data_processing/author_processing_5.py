import json


def load_authors():
    authors = []
    with open('authors.txt', 'r') as f:
        for line in f.readlines():
            authors.append(json.loads(line))
    return authors


def load_books(file='./filtered_books_4.txt'):
    books = []
    with open(file, 'r') as f:
        for line in f.readlines():
            books.append(json.loads(line))
    return books


def write_all_autohrs(authors):
    data = []
    for author in authors:
        data.append(json.dumps(author) + '\n')
    with open('authors_filtered.txt', 'w') as f:
        f.writelines(data)


def write_all_books(data):
    with open('./filtered_books_5.txt', 'w') as f:
        f.writelines(data)


def print_all(authors):
    for author in authors:
        print(author)


if __name__ == '__main__':
    authors = load_authors()
    print(f'Loaded {len(authors)} Authors')
    books = load_books()
    print(f'Loaded {len(books)} Books')

    required_authors = set()

    for book in books:
        for author in book['authors']:
            required_authors.add(author)

    print(f'Generated required authors set: {len(required_authors)}')

    authors_outlaws = []
    authors_filtered = []
    for author in authors:
        if author['key'] in required_authors:
            authors_filtered.append(author)
            required_authors.remove(author['key'])

    write_all_autohrs(authors_filtered)

    print(f'Remaining Author Keys: {required_authors}')

    books_filtered = []
    for book in books:
        for author in book['authors']:
            if author not in required_authors:
                break
        else:
            continue
        books_filtered.append(json.dumps(book) + '\n')

    print(f'Removed {len(books) - len(books_filtered)} books')
    write_all_books(books_filtered)
