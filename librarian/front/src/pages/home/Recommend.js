import axios from "axios"
import { useEffect, useState } from "react"
import BookCardCompact from "../../components/BookCardCompact"
import LibraryBookPupup from "../../components/LibraryBookPopup"
import { API } from "../../enviroment"
import { usePopup } from "../../components/pop-up/PopUpFrame"
import { DropDownSelect } from "../../components/drop-down/DropDown"
import LoadingSpinner from "../../components/loading-spinner/LoadingSpinner"
import "./Recommend.css"

export default function Recommend() {    

    const token = localStorage.getItem("token")

    const [preferences, setPreferences] = useState(undefined)
    const [bySimilarityOptions, setBySimilarityOptions] = useState([])
    const [byPreferences, setByPreferences] = useState(true)
    const [byBookId, setByBookId] = useState(undefined)
    const [found, setFound] = useState([])
    const [isWaiting, setIsWaiting] = useState(false)

    useEffect(() => {
        axios.get(API + '/user/preferences/',  { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => { setPreferences(res.data) })
            .catch(e => console.log(e))
    }, [])

    const handlePrefChanged = (prefs) => setPreferences(prefs)

    useEffect(() => {
        let options = []
        if (preferences !== undefined) {
            preferences?.library?.forEach(element => {
                options.push({label: element?.title, value: element?.id})
            });
            preferences?.readBooks?.forEach(element => {
                options.push({label: element?.title, value: element?.id})
            });
        }
        setBySimilarityOptions(options)
    }, [preferences])

    const handleSimilarBookPicked = (val) => {
        setByBookId(val)
        console.log(val);
    }

    const handleRecommend = () => {
        if (byPreferences) {
            setIsWaiting(true)
            axios.get(API + "/recommend/by/preferences",  { headers: {"Authorization" : `Bearer ${token}`} })
                .then(res => {
                    console.log(res.data)
                    setIsWaiting(false)
                    setFound(res.data)
                })
        }
        else if (!byPreferences && byBookId != undefined) {
            setIsWaiting(true)
            axios.get(API + "/recommend/by/book/" + byBookId,  { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => {
                console.log(res.data)
                setIsWaiting(false)
                setFound(res.data)
            })
        }
    }

    const detailsPopUp = usePopup()
    const [book, setBook] = useState(undefined)

    return(
        <div className="w-100 standard-padding">
            
            <div className="flex center gap-xs" style={{margin:'0 24px'}}>
                <div className="radio-inputs regular_border"> 
                    <label className="radio">
                        <input type="radio" checked={byPreferences} onClick={() => setByPreferences(true)}/>
                        <span className="name">By Preferences</span>
                    </label>
                    <label className="radio">
                        <input type="radio" checked={!byPreferences} onClick={() => setByPreferences(false)}/>
                        <span className="name">By Similarity</span>
                    </label>
                </div>
                {!byPreferences && <DropDownSelect placeholder={"Book"} icon={"book"} options={bySimilarityOptions} callback={handleSimilarBookPicked} enabled={true}/>}
                <button className={`outline-button ${isWaiting ? 'disabled-outline-button' : ''}`} style={{height:'49px', translate:'0 -1px', padding:'0 16px'}} onClick={handleRecommend} enabled={!isWaiting}>Recommend me Some Books</button>
            </div>

            {found.length == 0 && !isWaiting && <div className="dashed-card flex column center vi-spacer-m" style={{margin:'12px 24px'}}>
                <p className="section-title">Nothing To Show</p>
                <p className="tutorial-text neutral">Select the recommendation algorithm and hit recommend!.</p>
            </div>}

            {isWaiting && <div className="dashed-card flex justify-center vi-spacer-m" style={{margin:'12px 24px'}}>
                <LoadingSpinner label="Please wait while we get your recommendation!"/>    
            </div>}

            {found.length != 0 && !isWaiting && <div className="w-100 standard-padding gap-s" style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr 1fr 1fr'}}>
                {found.map((book, index) => { return(
                    <div className="recommendation-border" style={{animationDelay: index * 0.4 + 's'}}>
                        <p className="recommendation-index">{index + 1}</p>
                        <BookCardCompact
                            delay={index * 0.4}
                            book={book}
                            isLibraryView={false}
                            isInLibrary={preferences?.library.find((item) => item.id == book.id) != undefined}
                            onPrefUpdateCallback={handlePrefChanged}
                            onClick={() => {
                                setBook(book)
                                detailsPopUp.showPopup()
                            }}
                        />
                    </div>
                )})}
            </div>}

            <LibraryBookPupup token={token} popup={detailsPopUp} book={book} isLibraryView={false} isInLibrary={preferences?.library?.find((item) => item.id == book?.id) != undefined} onPrefUpdateCallback={handlePrefChanged}/>
        </div>
    )
}