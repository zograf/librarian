import json
import math


def get_all(file='./books.txt'):
    books = []
    with open(file, 'r') as f:
        for line in f.readlines():
            books.append(line)
    return books


def write_all(data):
    with open('./filtered_books_1.txt', 'w') as f:
        f.writelines(data)


def get_books_without(column):
    found = []
    with open('./books.txt', 'r') as f:
        for line in f.readlines():
            book = json.loads(line)
            if column not in book.keys(): found.append(line)
    return found


def get_books_with(column, file='./books.txt'):
    found = []
    with open(file, 'r') as f:
        for line in f.readlines():
            book = json.loads(line)
            if column in book.keys(): found.append(line)
    return found


def remove_books_without(column):
    print(f'--> Removing without \"{column}\"...')
    removed = list(set(get_all()) - set(get_books_without(column)))
    print(f'Remaining amount - {len(removed)}')
    write_all(removed)
    print(f'Done removing without \"{column}\" column!')


def remove_books_with(column):
    print(f'--> Removing with \"{column}\"...')
    removed = list(set(get_all()) - set(get_books_with(column)))
    print(f'Remaining amount - {len(removed)}')
    write_all(removed)
    print(f'Done removing with \"{column}\" column!')


def remove_columns():
    remove_books_without('authors')
    #remove_books_with('notifications')
    #remove_books_with('series')
    #remove_books_with('works')
    #remove_books_with('translated_titles')
    #remove_books_with('original_languages')
    #remove_books_with('cover_edition')
    #remove_books_with('number_of_editions')


def stats(file):
    total = len(get_all(file))
    for key in ['subject_places', 'subject_people', 'subject_times']:
        amount = len(get_books_with(key, file))
        print(f'With \"{key}\" - {amount} ({round((amount / total) * 100, 2) }%)')


if __name__ == '__main__':
    remove_columns()
    #stats('./filtered_books.txt')
    #stats('./even_more_filtered_books.txt')