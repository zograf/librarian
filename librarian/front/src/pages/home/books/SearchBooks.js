import axios from "axios"
import { useEffect, useState } from "react"
import { API } from "../../../enviroment"
import BookCardCompact from "../../../components/BookCardCompact"
import { usePopup } from "../../../components/pop-up/PopUpFrame"
import LibraryBookPupup from "../../../components/LibraryBookPupup"

export default function SearchBooks() {
    const token = localStorage.getItem("token")

    const [phrase, setPhrase] = useState("")
    const handlePhrase = (e) => setPhrase(e.target.value)
    const [preferences, setPreferences] = useState(undefined)
    const [found, setFound] = useState([])

    useEffect(() => {
        axios.get(API + '/user/preferences/',  { headers: {"Authorization" : `Bearer ${token}`} })
        .then(res => { console.log(res.data); setPreferences(res.data) })
        .catch(e => console.log(e))
    }, [])

    const search = (e) => {
        if (e.code == 'Enter' && phrase.length >= 3)
            axios.post(API + "/books/like", null, { params: { phrase : phrase }})
                .then(resp => {
                    console.log(resp);
                    setFound(resp.data)
                })
    }

    const detailsPopUp = usePopup()
    const [book, setBook] = useState(undefined)

    return(
        <div className="w-100 standard-padding">
            <div className="">
                <p className="section-title">Search Books By Name</p>
                <div className="input-wrapper regular-border v-spacer-xs" style={{paddingRight:'4px'}}>
                    <span className="material-symbols-outlined icon input-icon">search</span>
                    <input placeholder="Search" value={phrase} onChange={handlePhrase} onKeyUp={search}/>
                </div>
            </div>
            <div className="w-100 standard-padding gap-s" style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr 1fr 1fr'}}>
                {found.map(book => { return(<BookCardCompact 
                    book={book} 
                    isLiked={preferences.library.find((item) => item.id == book.id) != undefined}
                    onClick={() => {
                        setBook(book)
                        detailsPopUp.showPopup()
                    }}
                />)})}
            </div>
            <LibraryBookPupup token={token} popup={detailsPopUp} book={book}/>
        </div>
    )
}