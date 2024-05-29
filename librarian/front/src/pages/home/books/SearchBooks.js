import axios from "axios"
import { useEffect, useState } from "react"
import { API } from "../../../enviroment"
import BookCardCompact from "../../../components/BookCardCompact"
import { usePopup } from "../../../components/pop-up/PopUpFrame"
import LibraryBookPupup from "../../../components/LibraryBookPopup"
import LoadingSpinner from "../../../components/loading-spinner/LoadingSpinner"

export default function SearchBooks() {
    const token = localStorage.getItem("token")

    const [phrase, setPhrase] = useState("")
    const handlePhrase = (e) => setPhrase(e.target.value)
    const [preferences, setPreferences] = useState(undefined)
    const handlePrefChanged = (prefs) => setPreferences(prefs)
    const [foundBooks, setFoundBooks] = useState([])
    const [foundAuthors, setFoundAuthors] = useState([])
    const [isSearching, setIsSearching] = useState(false)
    const [byTitle, setByTitle] = useState(true)
    const handleSearchTypeChange = (val) => {
        if(val != byTitle) {
            setPhrase("")
            setFoundBooks([])
            setFoundAuthors([])
            setTargetAuthorId(-1)
        }
        setByTitle(val)
    }
    const [targetAuthorId, setTargetAuthorId] = useState(-1)

    useEffect(() => {
        axios.get(API + '/user/preferences/',  { headers: {"Authorization" : `Bearer ${token}`} })
        .then(res => { console.log(res.data); setPreferences(res.data) })
        .catch(e => console.log(e))
    }, [])

    const search = (e) => {
        if (byTitle && e.code == 'Enter' && phrase.length >= 3 && !isSearching) {
            setIsSearching(true)
            axios.post(API + "/books/like", null, { params: { phrase : phrase }})
                .then(resp => {
                    console.log(resp);
                    setIsSearching(false)
                    setFoundBooks(resp.data)
                })
        }
        else if (!byTitle && phrase.length >= 4) {
            axios.post(API + "/authors/like", null, { params: { phrase : phrase }})
            .then(resp => {
                setFoundAuthors(resp.data)
                setTargetAuthorId(-1)
            })
        }
        else if (!byTitle && phrase.length == 0) {
            setFoundAuthors([])
            setFoundBooks([])
            setTargetAuthorId(-1)
        }
    }

    const searchByAuthor = (id) => {
        setTargetAuthorId(id)
        if (id != -1) {
            setIsSearching(true)
            axios.post(API + "/books/author", null, { params: { authorId : id }})
                .then(resp => {
                    console.log(resp);
                    setIsSearching(false)
                    setFoundBooks(resp.data)
                })
        }
    }

    const detailsPopUp = usePopup()
    const [book, setBook] = useState(undefined)

    return(
        <div className="w-100 standard-padding">
            
            <div className="flex center gap-xs" style={{margin:'0 24px'}}>
                <div className="radio-inputs regular_border vi-spacer-xxs"> 
                    <label className="radio">
                        <input type="radio" checked={byTitle} onClick={() => handleSearchTypeChange(true)}/>
                        <span className="name">By Title</span>
                    </label>
                    <label className="radio">
                        <input type="radio" checked={!byTitle} onClick={() => handleSearchTypeChange(false)}/>
                        <span className="name">By Author</span>
                    </label>
                </div>
                <div className={`input-wrapper regular-border ${isSearching ? 'disabled-input' : ''}`}>
                    <span className="material-symbols-outlined icon input-icon">search</span>
                    <input placeholder={byTitle ? 'Search By Title' : 'Search For Author'} value={phrase} onChange={handlePhrase} onKeyUp={search}/>
                </div>
                {(phrase.length > 0 && ((byTitle && phrase.length < 3) || (!byTitle && phrase.length < 4)) || foundBooks.length != 0) &&
                    <p className="flex center showing card-body" 
                        style={{
                            height:'50px', 
                            transition:'all 0.08s ease-in-out', 
                            backgroundColor: foundBooks.length == 0 ? 'rgb(var(--error))' : 'rgb(var(--accent))', 
                            color: foundBooks.length == 3 ? 'rgb(var(--on-error))' : 'rgb(var(--on-primary-dark))',
                            borderRadius:'var(--input-radius)',
                            padding:'0 16px'
                        }}>{foundBooks.length == 0 ? 'Phrase Too Short' : foundBooks.length + ' Results'}</p>
                    }
            </div>

            {foundAuthors.length > 0 && <div className="flex center wrap gap-xs" style={{margin:'12px 24px'}}>{
                foundAuthors.map((item) => {
                    return (<button className={`flex center showing ${targetAuthorId == item.id ? 'text-button-selected' : 'outline-button'}`} onClick={() => { searchByAuthor(item.id) }}>
                        {item.name} 
                    </button>)}
                )
            }</div>}

            {((!byTitle && foundAuthors.length == 0) || (!byTitle && foundAuthors.length > 0 && foundBooks.length == 0) || (byTitle && foundBooks.length == 0)) && !isSearching && 
                <div className="dashed-card flex column center" style={{margin:'12px 24px'}}>
                    <p className="section-title">Nothing To Show</p>
                    <p className="tutorial-text neutral">{
                        byTitle ? 'Search for books by their title.' : 
                        !byTitle && foundAuthors.length == 0 ? 'Search for books by author.' : 'Select the Author to get your results.'
                    }</p>
                </div>
            }

            {isSearching && <div className="dashed-card flex justify-center vi-spacer-m" style={{margin:'12px 24px'}}>
                <LoadingSpinner label="Please wait while we get your results!"/>    
            </div>}
            
            {foundBooks.length != 0 && !isSearching && <div className="w-100 showing gap-s" style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr 1fr 1fr', padding:'12px 24px'}}>
                {foundBooks.map(book => { return(<BookCardCompact 
                    book={book} 
                    isLibraryView={false}
                    isInLibrary={preferences.library.find((item) => item.id == book.id) != undefined}
                    onPrefUpdateCallback={handlePrefChanged}
                    onClick={() => {
                        setBook(book)
                        detailsPopUp.showPopup()
                    }}
                />)})}
            </div>}
            <LibraryBookPupup token={token} popup={detailsPopUp} book={book} isLibraryView={false} isInLibrary={preferences?.library?.find((item) => item.id == book?.id) != undefined} onPrefUpdateCallback={handlePrefChanged}/>
        </div>
    )
}