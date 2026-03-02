function VideoComponent() {
    return (
        <div>
            <h1>Exemple de Composant Vidéo</h1>

            {/* Cas non-conforme 1 : autoPlay */}
            <p>Vidéo avec autoPlay (sera flaggée):</p>
            <video controls autoPlay muted loop>
                <source src="video-autoplay.mp4" type="video/mp4" />
                Votre navigateur ne supporte pas la balise vidéo.
            </video>

            <hr />

            {/* Cas non-conforme 2 : preload="auto" */}
            <p>Vidéo avec preload="auto" (sera flaggée):</p>
            <video controls preload="auto">
                <source src="video-preload-auto.mp4" type="video/mp4" />
                Votre navigateur ne supporte pas la balise vidéo.
            </video>

            <hr />

            {/* Cas non-conforme 3 : preload="metadata" */}
            <p>Vidéo avec preload="metadata" (sera flaggée):</p>
            <video controls preload="metadata">
                <source src="video-preload-metadata.mp4" type="video/mp4" />
                Votre navigateur ne supporte pas la balise vidéo.
            </video>

            <hr />

            {/* Cas conforme : preload="none" */}
            <p>Vidéo avec preload="none" (OK, ne sera pas flaggée):</p>
            <video controls preload="none">
                <source src="video-preload-none.mp4" type="video/mp4" />
                Votre navigateur ne supporte pas la balise vidéo.
            </video>

            <hr />

            {/* Cas conforme : Élément audio (non ciblé par la règle) */}
            <p>Audio avec preload="auto" (OK, ne sera pas flaggée car audio et non video):</p>
            <audio controls preload="auto">
                <source src="audio-example.mp3" type="audio/mp3" />
                Votre navigateur ne supporte pas la balise audio.
            </audio>
        </div>
    );
}

export default VideoComponent;