import { useEffect, useState } from "react"
import BookCardCompact from "../../components/BookCardCompact"
import LibraryBookPupup from "../../components/LibraryBookPopup"
import { API } from "../../enviroment"
import axios from "axios"
import { usePopup } from "../../components/pop-up/PopUpFrame"
import BookCardWide from "../../components/BookCardWide"

export default function Trending() {
    const token = localStorage.getItem("token")
    const [trending, setTrending] = useState([])
    const [hot, setHot] = useState([])
    const [preferences, setPreferences] = useState(undefined)

    useEffect(() => {
        axios.get(API + '/user/preferences/',  { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => { 

                console.log(res.data); 
                setPreferences(res.data) 

                axios.get(API + '/books/trending/',  { headers: {"Authorization" : `Bearer ${token}`} })
                    .then(res => { 
                        console.log(res.data)
                        setTrending(res.data.filter(item => !item.newToTrending))
                        setHot(res.data.filter(item => item.newToTrending))
                    })
                    .catch(e => console.log(e))
            
            })
            .catch(e => console.log(e))
    }, [])

    const handlePrefChanged = (prefs) => setPreferences(prefs)

    const detailsPopUp = usePopup()
    const [book, setBook] = useState(undefined)

    return(
        <div className="w-100 standard-padding">
            {trending.length == 0 && <div className="dashed-card flex column center" style={{margin:'12px 24px'}}>
                <p className="section-title">Nothing To Show</p>
                <p className="tutorial-text neutral">Curently there are not trending books.</p>
            </div>}

            {hot.length != 0 && <div className="v-spacer-xl">
                <p className="section-title">Hot Titles</p>
                <div className="w-100 showing gap-s" style={{display:'grid', gridTemplateColumns:'1fr 1fr'}}>
                    {hot.map(book => { return(<BookCardWide 
                        book={book} 
                        isLibraryView={false}
                        isRead={preferences?.readBooks?.find((item) => item.id == book.id) != undefined}
                        isInLibrary={preferences?.library?.find((item) => item.id == book.id) != undefined || preferences?.readBooks?.find((item) => item.id == book.id) != undefined}
                        onPrefUpdateCallback={handlePrefChanged}
                        onClick={() => {
                            setBook(book)
                            detailsPopUp.showPopup()
                        }}
                    />)})}
                </div>
            </div>}

            {trending.length != 0 && <div>
                <p className="section-title">Other Trending Titles</p>
                <div className="w-100 showing gap-s" style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr 1fr 1fr'}}>
                    {trending.map(book => { return(<BookCardCompact 
                        book={book} 
                        isLibraryView={false}
                        isRead={preferences?.readBooks?.find((item) => item.id == book.id) != undefined}
                        isInLibrary={preferences?.library?.find((item) => item.id == book.id) != undefined}
                        onPrefUpdateCallback={handlePrefChanged}
                        onClick={() => {
                            setBook(book)
                            detailsPopUp.showPopup()
                        }}
                    />)})}
                </div>
            </div>}
            <LibraryBookPupup 
                token={token} 
                popup={detailsPopUp}
                book={book} 
                isLibraryView={false} 
                isRead={preferences?.readBooks?.find((item) => item.id == book?.id) != undefined}
                isInLibrary={preferences?.library?.find((item) => item.id == book?.id) != undefined} 
                onPrefUpdateCallback={handlePrefChanged}/>
        </div>
    )
}