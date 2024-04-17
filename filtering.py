import json
import string
from random import random

common_fields = []
removed_fields = []


def write_all(data):
    with open('./even_more_filtered_books.txt', 'w') as f:
        f.writelines(data)

# ['subjects', 'key', 'title', 'authors', 'type', 'latest_revision', 'revision', 'created', 'last_modified']
# ['covers', 'subject_places', 'subject_people', 'subject_times', 'location']


def print_filtered():
    with open('./even_more_filtered_books.txt', 'r') as f:
        for line in f.readlines():
            print(json.dumps(json.loads(line), indent=4))
            print('---------------------------------------------------------------------')


def main():
    fixed = []
    with open('./filtered_books.txt', 'r') as f:
        for line in f.readlines():
            # print(json.dumps(json.loads(line), indent=4))
            # print('---------------------------------------------------------------------')
            try:
                book = json.loads(line)
            except Exception:
                print(line)
                continue
            if len(common_fields) == 0: common_fields.extend(book.keys())
            for_removal = []

            for field in common_fields:
                if field not in book.keys():
                    for_removal.append(field)

            for removed in for_removal:
                common_fields.remove(removed)

            for key in book.keys():
                if key not in common_fields and key not in removed_fields:
                    removed_fields.append(key)

            # description
            if 'description' in book.keys():
                description = book['description']
                if type(description) != dict:
                    book['description'] = str(description)
                elif type(description) == dict:
                    book['description'] = str(description['value'])
            else: book['description'] = ''

            # first_sentence
            if 'first_sentence' in book.keys():
                first_sentence = book['first_sentence']
                book['first_sentence'] = first_sentence['value']
            else: book['first_sentence'] = ''

            # authors
            author_keys = []
            for author in book['authors']:
                author_keys.append(author['author']['key'].split('/')[-1])
            book['authors'] = author_keys

            # lc_classifications
            if 'lc_classifications' in book.keys(): book.pop('lc_classifications')

            # dewey_number
            if 'dewey_number' in book.keys(): book.pop('dewey_number')

            # subtitle
            if 'subtitle' not in book.keys(): book['subtitle'] = ''

            # links
            if 'links' in book.keys(): book.pop('links')

            # excerpts
            if 'excerpts' in book.keys(): book.pop('excerpts')

            # created
            if 'created' in book.keys(): book.pop('created')

            # last_modified
            if 'last_modified' in book.keys(): book.pop('last_modified')

            # latest_revision
            if 'latest_revision' in book.keys(): book.pop('latest_revision')

            # revision
            if 'revision' in book.keys(): book.pop('revision')

            # type
            if 'type' in book.keys(): book.pop('type')

            # first_publish_date
            if 'first_publish_date' in book.keys():
                year = book['first_publish_date'].split(',')[-1].split(' ')[-1]
                book.pop('first_publish_date')
                book['first_published_year'] = year
            else: book['first_published_year'] = -1

            # covers
            if 'covers' in book.keys():
                book['cover'] = f'https://covers.openlibrary.org/b/id/{book["covers"][-1]}-M.jpg'
                book.pop('covers')
            else: book['cover'] = ''

            # location
            if 'location' not in book.keys(): book['location'] = ""

            fixed.append(json.dumps(book) + '\n')

    print(f'Common Fields: {common_fields}')
    write_all(fixed)
    print(f'Removed Fields: {removed_fields}')


if __name__ == '__main__':
    print_filtered()
    #main()