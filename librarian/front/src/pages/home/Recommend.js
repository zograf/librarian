import axios from "axios"
import { useEffect, useState } from "react"
import BookCardCompact from "../../components/BookCardCompact"
import LibraryBookPupup from "../../components/LibraryBookPupup"
import { API } from "../../enviroment"
import { usePopup } from "../../components/pop-up/PopUpFrame"

export default function Recommend() {    

    const token = localStorage.getItem("token")
    const [found, setFound] = useState([])

    const handleRecommend = () => {
        axios.get(API + "/recommend/by/preferences",  { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => {
                console.log(res)
                setFound(res.data)
            })
    }

    const detailsPopUp = usePopup()
    const [book, setBook] = useState(undefined)

    return(
        <div className="w-100 standard-padding">
            <button className="solid-accent-button" onClick={handleRecommend}>Recommend</button>

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