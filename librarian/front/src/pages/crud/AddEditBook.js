import { useEffect, useState } from "react"
import { DropDownSelect } from "../../components/drop-down/DropDown"
import axios from "axios"
import { API } from "../../enviroment"

export default function AddEditBook() {

    const [title, setTitle] = useState("")
    const [authors, setAuthors] = useState([])
    const [subjects, setSubjects] = useState([])
    const [description, setDescription] = useState("")
    const [firstSentence, setFirstSentence] = useState("")
    const [firstPublishedYear, setFirstPublishedYear] = useState()
    const [coverUrl, setCoverUrl] = useState("")
    const [age, setAge] = useState(-1)

    const targetAges = [{ label: "Juvenile", value: 0 }, { label: "Young Adult", value: 1 }, { label: "Adult", value: 2 }]

    // Subjects
    const [subjectsPhrase, setSubjectsPhrase] = useState("")
    const [foundSubjects, setFoundSubjects] = useState([])
    const [subjectsSearchSent, setSubjectsSearchSent] = useState(false)
    const searchSubjects = (e) => {
        if (subjectsPhrase.length >= 3)
            axios.post(API + "/subjects/like", null, { params: { phrase : subjectsPhrase }})
                .then(resp => {
                    setFoundSubjects(resp.data)
                    setSubjectsSearchSent(true)
                })
        else if (subjectsPhrase.length == 0) clearSubjects()
        else {
            setFoundSubjects([])
            setSubjectsSearchSent(true)
        }
    }
    const clearSubjects = () => {
        setFoundSubjects([])
        setSubjectsSearchSent(true)
        setSubjectsPhrase("")
    }
    const addSubject = (subject) => {
        setSubjects((prevList) => {
            return [...prevList, subject]
        })
    }
    const removeSubject = (subject) => {
        setSubjects((prevList) => {
            return prevList.filter((x) => x.id !== subject.id)
        })
    }

    // Authors
    const [authorsPhrase, setAuthorsPhrase] = useState("")
    const [foundAuthors, setFoundAuthors] = useState([])
    const [authorsSearchSent, setAuthorsSearchSent] = useState(false)
    const searchAuthors = (e) => {
        if (authorsPhrase.length >= 4)
            axios.post(API + "/authors/like", null, { params: { phrase : authorsPhrase }})
                .then(resp => {
                    setFoundAuthors(resp.data)
                    setAuthorsSearchSent(true)
                })
        else if (authorsPhrase.length == 0) clearAuthors()
        else {
            setFoundAuthors([])
            setAuthorsSearchSent(true)
        }
    }
    const clearAuthors = () => {
        setFoundAuthors([])
        setAuthorsSearchSent(true)
        setAuthorsPhrase("")
    }
    const addAuthor = (authro) => {
        setAuthors((prevList) => {
            return [...prevList, authro]
        })
    }
    const removeAuthor = (authro) => {
        setAuthors((prevList) => {
            return prevList.filter((x) => x.id !== authro.id)
        })
    }

    // Save
    const save = () => {
        // TODO Save book
        cleanUp()
    }
    const cleanUp = () => {
        clearAuthors()
        clearSubjects()
        setTitle("")
        setAuthors([])
        setSubjects([])
        setDescription("")
        setFirstSentence("")
        setFirstPublishedYear("")
        setCoverUrl("")
        setAge(-1)
        // Or refresh ?? :D
    }

    return(
        <div className="standard-padding">
            <div className="flex center space-between v-spacer-m">
                <p className="section-title" style={{marginBottom:'0'}}>Main Information</p>
                <button className="solid-button" onClick={save}>Save new book</button>
            </div>
            <div className="gap-xs" style={{display:'grid', gridTemplateColumns:'1fr 1fr'}}>
                <div>
                    <div className="input-wrapper regular-border v-spacer-xs">
                        <span className="material-symbols-outlined icon input-icon">titlecase</span>
                        <input placeholder="Title" value={title} onChange={(e) => setTitle(e.target.value)} />
                    </div>
                    <div className="input-wrapper regular-border v-spacer-xs">
                        <span className="material-symbols-outlined icon input-icon">auto_stories</span>
                        <input placeholder="First Sentence [Optional]" value={firstSentence} onChange={(e) => setFirstSentence(e.target.value)} />
                    </div>
                    <div className="input-wrapper regular-border v-spacer-xs">
                        <span className="material-symbols-outlined icon input-icon">123</span>
                        <input placeholder="First Published Year" type="number" value={firstPublishedYear} onChange={(e) => setFirstPublishedYear(e.target.value)} />
                    </div>
                    <div className="input-wrapper regular-border v-spacer-xs">
                        <span className="material-symbols-outlined icon input-icon">image</span>
                        <input placeholder="Cover Url [Optional]" value={coverUrl} onChange={(e) => setCoverUrl(e.target.value)} />
                    </div>
                    <DropDownSelect placeholder={"Target Age"} icon={"wc"} options={targetAges} callback={(val) => setAge(val)} enabled={true}/>
                </div>
                <div className="input-wrapper regular-border v-spacer-xs" style={{height:'100%', alignItems:'unset'}}>
                    <span className="material-symbols-outlined icon input-icon vi-spacer-m">description</span>
                    <textarea className="silent-scroll" placeholder="Description [Optional]" value={description} onChange={(e) => setDescription(e.target.value)} style={{height:'100%', padding:'18px 10px'}}/>
                </div>

            </div>

            <div className="flex center gap-l v-spacer-s vi-spacer-xl">
                <div>
                    <p className="section-title" style={{marginBottom:"0"}}>Subjects</p>
                    <p className="card-label neutral">At least 5 required</p>
                </div>
                <div className="input-wrapper regular-border" style={{paddingRight:'4px', width:'25%'}}>
                    <span className="material-symbols-outlined icon input-icon">search</span>
                    <input placeholder="Search Subjects" value={subjectsPhrase} onChange={(e) => setSubjectsPhrase(e.target.value)} onKeyUp={searchSubjects}/>
                    {subjectsSearchSent && foundSubjects.length > 0 && <button className="icon-button"><span className="material-symbols-outlined icon" onClick={clearSubjects}>cancel</span></button>}
                </div>
            </div>
            {subjects.length > 0 && <div className="showing vi-spacer-s">
                <div className="flex center wrap gap-xs">{
                    subjects.map((keyword) => {return (
                        <div className="solid-chip flex center showing" style={{paddingRight:'0px'}}>
                            <p>{keyword.keyword}</p>
                            <button className="icon-button showing"><span className="material-symbols-outlined icon" onClick={() => { removeSubject(keyword) }}>cancel</span></button>
                        </div>
                )})
                }</div>
            </div>}
            <div className="flex center wrap gap-xs vi-spacer-s">{
                foundSubjects.map((keyword) => {
                        var found = subjects.find(x => x.id == keyword.id) != undefined
                        return (<button className={`flex center showing ${found ? 'text-button-selected' : 'outline-button'}`} onClick={() => { if(!found) addSubject(keyword) }}>
                            {keyword.keyword} <p className="neutral hi-spacer-xs">{keyword.relevance}</p>
                        </button>)}
                    )
            }</div>
            

            <div className="flex center gap-l v-spacer-s vi-spacer-xl">
                <div>
                    <p className="section-title" style={{marginBottom:"0"}}>Authors</p>
                    <p className="card-label neutral hi-spacer-xxs">At least 1 required</p>
                </div>
                <div className="input-wrapper regular-border" style={{paddingRight:'4px', width:'25%'}}>
                    <span className="material-symbols-outlined icon input-icon">search</span>
                    <input placeholder="Search Authors" value={authorsPhrase} onChange={(e) => setAuthorsPhrase(e.target.value)} onKeyUp={searchAuthors}/>
                    {authorsSearchSent && foundAuthors.length > 0 && <button className="icon-button"><span className="material-symbols-outlined icon" onClick={clearAuthors}>cancel</span></button>}
                </div>
            </div>
            {authors.length > 0 && <div className="showing">
                <div className="flex center wrap gap-xs">{
                    authors.map((author) => {return (
                        <div className="solid-chip flex center showing" style={{paddingRight:'0px'}}>
                            <p>{author.name}</p>
                            <button className="icon-button showing"><span className="material-symbols-outlined icon" onClick={() => { removeAuthor(author) }}>cancel</span></button>
                        </div>
                )})}</div>
            </div>}
            <div className="flex center wrap gap-xs vi-spacer-s">{
                foundAuthors.map((author) => {
                        var found = authors.find(x => x.id == author.id) != undefined
                        return (<button className={`flex center showing ${found ? 'text-button-selected' : 'outline-button'}`} onClick={() => { if(!found) addAuthor(author) }}>
                            {author.name}
                        </button>)}
                    )
            }</div>

        </div>
    )
}