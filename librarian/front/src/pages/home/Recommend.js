import axios from "axios"
import { useEffect, useState } from "react"
import BookCardCompact from "../../components/BookCardCompact"
import LibraryBookPupup from "../../components/LibraryBookPupup"
import { API } from "../../enviroment"
import { usePopup } from "../../components/pop-up/PopUpFrame"
import { DropDownSelect } from "../../components/drop-down/DropDown"

export default function Recommend() {    

    const token = localStorage.getItem("token")

    const [preferences, setPreferences] = useState(undefined)
    const [bySimilarityOptions, setBySimilarityOptions] = useState([])
    const [byPreferences, setByPreferences] = useState(true)
    const [byBookId, setByBookId] = useState(undefined)
    const [found, setFound] = useState([])

    useEffect(() => {
        axios.get(API + '/user/preferences/',  { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => { setPreferences(res.data) })
            .catch(e => console.log(e))
    }, [])

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
            axios.get(API + "/recommend/by/preferences",  { headers: {"Authorization" : `Bearer ${token}`} })
                .then(res => {
                    console.log(res)
                    setFound(res.data)
                })
        }
        else if (!byPreferences && byBookId != undefined) {
            axios.get(API + "/recommend/by/book/" + byBookId,  { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => {
                console.log(res)
                setFound(res.data)
            })
        }
    }

    const detailsPopUp = usePopup()
    const [book, setBook] = useState(undefined)

    return(
        <div className="w-100 standard-padding">
            
            <div className="flex center gap-xs">
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
                <button className="outline-button" style={{height:'49px', translate:'0 -1px', padding:'0 16px'}} onClick={handleRecommend}>Recommend me Some Books</button>

            </div>

            {found.length == 0 && <div className="dashed-card flex column center vi-spacer-m">
                <p className="section-title">Nothing To Show</p>
                <p className="tutorial-text">Select the recommendation algorithm and hit recommend!.</p>
            </div>}

            <div className="w-100 standard-padding gap-s" style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr 1fr 1fr'}}>
                {found.map(book => { return(<BookCardCompact 
                    book={book}
                    isLiked={false}
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