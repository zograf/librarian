import "./LoadingSpinner.css"

export default function LoadingSpinner({label="Loading ..."}) {
    return(
        <div className="flex center column">
            <div className="loader v-spacer-l vi-spacer-xs"> </div>
            <p class="loader-text">{label}</p>
        </div>
    )
}