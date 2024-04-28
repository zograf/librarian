import json

def main():
    with open("../filtered_books_7.txt", "r") as f:
        books = [json.loads(line) for line in f.readlines()]

    with open("../subjects.txt", "r") as f:
        subjects = [json.loads(line) for line in f.readlines()]


    subMap = {}
    counter = 0
    for book in books:
        for sub in book['subjects']:
            if sub in subMap:
                subMap[sub] += 1
            else:
                subMap[sub] = 1
        counter += 1
        if (counter % 10000 == 0):
            print(f"{counter}/{len(books)}")

    for subject in subjects:
        keyword = subject['keyword']
        if (keyword in subMap):
            subject['relevance'] = subMap[subject['keyword']]
        else:
            subject['relevance'] = 0
    
    with open("../filtered_subjects.txt", "w") as f:
        for subject in subjects:
            f.write(json.dumps(subject) + "\n")

if __name__=="__main__":
    main()