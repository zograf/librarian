import axios from "axios"
import { useEffect, useState } from "react"
import { API } from "../../enviroment"
import BookCardCompact from "../../components/BookCardCompact"
import { PopUpFrame, usePopup } from "../../components/pop-up/PopUpFrame"
import LibraryBookPupup from "../../components/LibraryBookPopup"

export default function LibraryPage() {
    let path = '/user/preferences/'
    const token = localStorage.getItem("token")
    const [preferences, setPreferences] = useState(undefined)
    const [mostCommon, setMostCommon] = useState([])

    useEffect(() => {
        axios.get(API + path,  { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => { console.log(res.data); setPreferences(res.data) })
            .catch(e => console.log(e))

        axios.get(API + "/recommend/stats", { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => { console.log(res.data); setMostCommon(res.data.mostCommonCategories) })
            .catch(e => console.log(e))

    }, [])

    const handlePrefChanged = (prefs) => setPreferences(prefs)

    const detailsPopUp = usePopup()
    const [book, setBook] = useState(undefined)

    return(
        <div className="standard-padding">

            
            {preferences?.library?.length == 0 && preferences?.readBooks?.length == 0 && <div className="dashed-card flex column center">
                <p className="section-title">Nothing in library</p>
                <p className="tutorial-text">Search for books or try our recommend feature!</p>
            </div>}

            {preferences?.library?.length != 0 && <p className="section-title">Want to Read</p>}
            {preferences?.library?.length != 0 &&  <div className="w-100 gap-s v-spacer-xl" style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr 1fr 1fr'}}>
                {preferences?.library.map(book => { return(
                    <BookCardCompact book={book} isInLibrary={true} isLibraryView={true} onClick={() => {
                        setBook(book)
                        console.log(book)
                        detailsPopUp.showPopup()
                    }}/>
                ) }
            )}
            </div>}

            {mostCommon.length > 0 && 
                <div className="showing">
                    <p className="section-title">Your most commonly read categories</p>
                    <div className="flex center wrap gap-xs v-spacer-xl">
                        {mostCommon.map((keyword) => {return (
                            <div className="solid-chip flex center showing">
                                <p>{keyword}</p>
                            </div>
                        )})}
                    </div>
            </div>}

            {preferences?.readBooks?.length != 0 && <p className="section-title">Read</p>}
            {preferences?.readBooks?.length != 0 &&  <div className="w-100 gap-s v-spacer-xl" style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr 1fr 1fr'}}>
                {preferences?.readBooks.map(book => { return(
                    <BookCardCompact book={book} isInLibrary={true} isLibraryView={true} isRead={true} isLiked={book?.liked} onClick={() => {
                        setBook(book)
                        console.log(book)
                        detailsPopUp.showPopup()
                    }}/>
                ) }
            )}
            </div>}

            <LibraryBookPupup token={token} popup={detailsPopUp} book={book} isLibraryView={true} isInLibrary={true} isRead={book?.liked != null} onPrefUpdateCallback={handlePrefChanged}/>
        </div>
    )
}