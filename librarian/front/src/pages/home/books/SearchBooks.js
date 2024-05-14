import axios from "axios"
import { useState } from "react"
import { API } from "../../../enviroment"

export default function SearchBooks() {
    const token = localStorage.getItem("token")

    const [phrase, setPhrase] = useState("")
    const handlePhrase = (e) => setPhrase(e.target.value)

    const [found, setFound] = useState([])

    const search = (e) => {
        if (phrase.length >= 3)
            axios.post(API + "/books/like", null, { params: { phrase : phrase }})
                .then(resp => {
                    console.log(resp);
                    setFound(resp.data)
                })
    }

    return(
        <div className="w-100 standard-padding">
            <div className="flex gap-xs center">
                <p className="section-title">Search Books By Name</p>
                <div className="input-wrapper regular-border v-spacer-xs" style={{paddingRight:'4px'}}>
                    <span className="material-symbols-outlined icon input-icon">search</span>
                    <input placeholder="Search" value={phrase} onChange={handlePhrase} onKeyUp={search}/>
                </div>
            </div>
        </div>
    )
}