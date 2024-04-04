import re

PATH = "C:\\Users\\Zograf\\Downloads\\ratings\\ratings.txt"

def main():
    ratings = {}
    with open(PATH, "r") as f:
        lines = f.readlines()
        for line in lines:
            s = line.split("\t")
            if len(s) < 3:
                continue
            work = s[0]
            rating = int(s[-2])
            if work in ratings:
                ratings[work].append(rating)
            else:
                ratings[work] = [rating]
    for k in ratings:
        v = ratings[k]
        if len(v) > 500:
            print(k)


if __name__=="__main__":
    main()