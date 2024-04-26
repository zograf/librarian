import json

def load_subjects(file='./subjects.txt'):
    books = []
    with open(file, 'r') as f:
        for line in f.readlines():
            books.append(json.loads(line))
    return books

fiction_list = ["fiction", "literature", "history", "magic", "mystery", "juvenile", "horror", "romance", "young", "thriller"]
nonfiction_list = ["psychology", "biography", "philosophy", "science", "mathematics", "politics"]
main_cat = ["ficiton", "nonfiction", "poetry"]

def main():
    subjects = load_subjects()
    for subject in subjects:
        if subject["keyword"] in fiction_list:
            subject["parent"] = "fiction"
        if subject["keyword"] in nonfiction_list:
            subject["parent"] = "nonfiction"

    subjects.append({'parent': 'nonfiction', 'keyword': 'nonfiction'})

    for subject in subjects:
        if subject["keyword"] == subject["parent"]:
            subject["parent"] = "category"
    
    with open('subjects_mod.txt', 'w') as f:
        for i in subjects:
            f.write(json.dumps(i) + "\n")

if __name__ == '__main__':
    main()