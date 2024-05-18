import axios from "axios"
import { useEffect, useState } from "react"
import { API } from "../../enviroment"
import BookCardCompact from "../../components/BookCardCompact"
import { PopUpFrame, usePopup } from "../../components/pop-up/PopUpFrame"
import LibraryBookPupup from "../../components/LibraryBookPupup"

export default function LibraryPage() {
    let path = '/user/preferences/'
    const token = localStorage.getItem("token")
    const [preferences, setPreferences] = useState(undefined)

    useEffect(() => {
        axios.get(API + path,  { headers: {"Authorization" : `Bearer ${token}`} })
        .then(res => { console.log(res.data); setPreferences(res.data) })
        .catch(e => console.log(e))
    }, [])

    const detailsPopUp = usePopup()
    const [book, setBook] = useState(undefined)
    const handleBack = (e) => {
        console.log(e);
        if(e.code == "KeyG") detailsPopUp.showPopup()
    }

    return(
        <div>
            <div className="w-100 standard-padding gap-s" style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr 1fr 1fr'}}>
                {preferences?.library.map(book => { return(
                    <BookCardCompact book={book} isLiked={true} inLibrary={true} onClick={() => {
                        setBook(book)
                        detailsPopUp.showPopup()
                    }}/>
                ) })}
            </div>
            <LibraryBookPupup token={token} popup={detailsPopUp} book={book}/>
            
        </div>
    )
}