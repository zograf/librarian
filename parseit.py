import re
import json
import time
import enchant

PATH = "C:\\Users\\Zograf\\Downloads\\books\\books.txt"
subjects = ["[Ff]antasy", "[Pp]olitics", "[Hh]orror", "[Tt]hriller", "[Rr]omance", "[Jj]uvenile", 
            "[Ss]elf[\s,-_]{1}[Hh]elp", "[Ss]ciencemathematcis", "[Bb]iography", "[Pp]sychology", 
            "[Pp]hilosophy", "[Mm]ystery", "[Mm]agic", "[Ll]iterature", "[Pp]oetry", "[Yy]oung"]

connectives = "a, an, and, are, as, at, be, but, by, for, if, in, into, is, it, no, not, of, on, or, such, that, the, their, then, there, these, they, this, to, was, will and with"

def main():
    number_of_books = 0
    batch_counter = 1
    subject_counter = 0
    con_subjects = connectives.split(", ")

    distinct_subjects = set()
    distinct_whole_subjects = set()

    subjects_regex = ""
    for s in subjects:
        subjects_regex += "(" + s + ")|"
    subjects_regex = subjects_regex[:-1]
    print(subjects_regex)

    english = enchant.Dict("en_US")
    
    start = time.time()

    to_write = open("./filtered_books.txt", "w+")
    with open(PATH, "r") as f:
        try:
            while True:
                batch = [next(f) for _ in range(100000)]
                for b in batch:
                    if(re.search(subjects_regex, b)):
                        #book = json.loads(b)
                        bb = b.split("\t")[-1]
                        book = json.loads(bb)
                        if ("subjects" in book and len(book["subjects"]) > 10):
                            subject_counter += 1
                            #for subject in book["subjects"]:
                            #    if (not re.search("^[A-Za-z\s]+$", subject)):
                            #        continue
                            #    if (not str.isascii(subject)):
                            #        continue
                            #    for word in subject.split(" "):
                            #        if len(word) and not english.check(word):
                            #            break
                            #    else:
                            #        distinct_whole_subjects.add(str.lower(subject))
                            #        for word in subject.split(" "):
                            #            if word not in con_subjects:
                            #                distinct_subjects.add(str.lower(word))
                            to_write.write(bb)
                                    
                        # Ostali uslovi ovde
                        #number_of_books += 1
                print(str(batch_counter) + "/345")
                batch_counter += 1
        except Exception as e:
            print(e)
    #for f in found:
    #    print(json.dumps(f, indent=2))
    end = time.time()
    print(end - start)
    #print(distinct_subjects)
    #print(distinct_whole_subjects)
    #print("Distinct subjects: " + str(len(distinct_subjects)))
    #print("Number of books: " + str(number_of_books))
    print("Number of filtered books: " + str(subject_counter))
    to_write.close()

if __name__ == '__main__':
    main()