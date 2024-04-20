import json
import re
import enchant

english = enchant.Dict("en_US")


def write_all(file, data):
    with open(file, 'w') as f:
        f.writelines(data)


def print_books(file):
    with open(file, 'r') as f:
        for line in f.readlines():
            print(json.dumps(json.loads(line), indent=4))
            print('---------------------------------------------------------------------')


def filter_subjects(subjects):
    filtered = []
    for subject in subjects:
        if not re.search("^[A-Za-z\s]+$", subject):
           continue
        if not str.isascii(subject):
           continue
        for word in subject.split(" "):
           if len(word) and not english.check(word):
               break
        else:
            filtered.append(subject.lower())
    return filtered

max_lines = 0
curr_line = 1

def main():
    global curr_line, max_lines
    fixed = []
    with (open('./filtered_books_1.txt', 'r') as f):
        lines = f.readlines()
        max_lines = len(lines)
        for line in lines:
            try:
                book = json.loads(line)
            except Exception:
                print(line)
                continue

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

            # subtitle
            if 'subtitle' not in book.keys(): book['subtitle'] = ''

            # remove unnecessary columns
            junks = ['location', 'lc_classifications', 'dewey_number', 'links', 'excerpts', 'created', 'last_modified', 'latest_revision', 'revision', 'type', 'subject_places', 'subject_times', 'subject_people', 'notifications', 'series', 'works', 'translated_titles', 'original_languages', 'cover_edition', 'number_of_editions']
            for junk in junks:
                if junk in book.keys(): book.pop(junk)

            # subjects
            filtered_subjects = filter_subjects(book['subjects'])
            book['subjects'] = filtered_subjects

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

            fixed.append(json.dumps(book) + '\n')
            curr_line += 1
            if (curr_line % 10000 == 0):
                print(f"{curr_line}/{max_lines}")

    write_all('./filtered_books_2.txt', fixed)


if __name__ == '__main__':
    print_books('./filtered_books_2.txt')
    #main()