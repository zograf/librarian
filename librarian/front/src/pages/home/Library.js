import axios from "axios"
import { useEffect, useState } from "react"
import { API } from "../../enviroment"
import BookCardCompact from "../../components/BookCardCompact"

export default function LibraryPage() {
    let path = '/user/preferences/'
    const token = localStorage.getItem("token")
    const [preferences, setPreferences] = useState(undefined)

    useEffect(() => {
        axios.get(API + path,  { headers: {"Authorization" : `Bearer ${token}`} })
        .then(res => { console.log(res.data); setPreferences(res.data) })
        .catch(e => console.log(e))
    }, [])

    // Details
    // const [viewDetails, setViewDetails] = useEffect(false)

    return(
        <div>
            <div className="w-100 standard-padding gap-s" style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr 1fr 1fr'}}>
                {preferences?.library.map(book => { return(<BookCardCompact book={book} isLiked={true} inLibrary={true}/>) })}
            </div>
        </div>
    )
}