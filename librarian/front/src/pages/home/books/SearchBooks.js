import axios from "axios"
import { useEffect, useState } from "react"
import { API } from "../../../enviroment"
import BookCardCompact from "../../../components/BookCardCompact"
import { usePopup } from "../../../components/pop-up/PopUpFrame"
import LibraryBookPupup from "../../../components/LibraryBookPupup"
import LoadingSpinner from "../../../components/loading-spinner/LoadingSpinner"

export default function SearchBooks() {
    const token = localStorage.getItem("token")

    const [phrase, setPhrase] = useState("")
    const handlePhrase = (e) => setPhrase(e.target.value)
    const [preferences, setPreferences] = useState(undefined)
    const [found, setFound] = useState([])
    const [isSearching, setIsSearching] = useState(false)

    useEffect(() => {
        axios.get(API + '/user/preferences/',  { headers: {"Authorization" : `Bearer ${token}`} })
        .then(res => { console.log(res.data); setPreferences(res.data) })
        .catch(e => console.log(e))
    }, [])

    const search = (e) => {
        if (e.code == 'Enter' && phrase.length >= 3 && !isSearching) {
            setIsSearching(true)
            axios.post(API + "/books/like", null, { params: { phrase : phrase }})
                .then(resp => {
                    console.log(resp);
                    setIsSearching(false)
                    setFound(resp.data)
                })
        }
    }

    const detailsPopUp = usePopup()
    const [book, setBook] = useState(undefined)

    return(
        <div className="w-100 standard-padding">
            
            <div className={`input-wrapper regular-border v-spacer-xs ${isSearching ? 'disabled-input' : ''}`} style={{margin:'0 24px'}}>
                <span className="material-symbols-outlined icon input-icon">search</span>
                <input placeholder="Search By Title" value={phrase} onChange={handlePhrase} onKeyUp={search}/>
            </div>

            {found.length == 0 && !isSearching && <div className="dashed-card flex column center" style={{margin:'12px 24px'}}>
                <p className="section-title">Nothing To Show</p>
                <p className="tutorial-text neutral">Search for books by their title.</p>
            </div>}

            {isSearching && <div className="dashed-card flex justify-center vi-spacer-m" style={{margin:'12px 24px'}}>
                <LoadingSpinner label="Please wait while we get your results!"/>    
            </div>}
            
            {found.length != 0 && !isSearching && <div className="w-100 standard-padding showing gap-s" style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr 1fr 1fr'}}>
                {found.map(book => { return(<BookCardCompact 
                    book={book} 
                    inLibrary={preferences.library.find((item) => item.id == book.id) != undefined}
                    onClick={() => {
                        setBook(book)
                        detailsPopUp.showPopup()
                    }}
                />)})}
            </div>}
            <LibraryBookPupup token={token} popup={detailsPopUp} book={book}/>
        </div>
    )
}