package com.zaad.common.domain;

import com.zaad.common.ZaadDomain;
import com.zaad.common.parser.VideoLineParser;
import com.zaad.common.util.ZaadVideoRanking;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author socurites, lks21c
 */
public class Video extends ZaadDomain {
    /**
     * 로거
     */
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Video.class);

	protected String zaadId;
	protected String videoId;
	protected int index;
	protected String tutorId;
	protected String playlistId;
	protected String title;
	protected String desc;
	protected Date creationDate;
	protected int viewCount;
	protected int likeCount;
	protected int dislikCount;
	protected int commentCount;
	
	protected Tutor tutor;
	protected Playlist playlist;
	
	protected double popularity;
	protected double recommendarity;
	
	private long timestamp;
	
	public Video() {
		super();
    }

    ;

    public Video(List<String> lines, String tutorId, String playlistId, String videoId) {
        super(lines);
		this.tutorId = tutorId;
		this.playlistId = playlistId;
		this.videoId = videoId;
		
		this.zaadId = this.playlistId + "_" + this.videoId;
		
		int i = 0;
		this.index = VideoLineParser.getIndexAsInt(lines.get(i++));
		
		this.title = lines.get(i++);
		try {
			this.creationDate = VideoLineParser.getCreationDate(lines.get(i++));
			this.desc = lines.get(i++);
			this.viewCount = VideoLineParser.getIntValFromText(lines.get(i++));
			this.likeCount = VideoLineParser.getIntValFromText(lines.get(i++));
			this.dislikCount = VideoLineParser.getIntValFromText(lines.get(i++));
			
			if ( lines.size() > i + 1 ) {
				this.commentCount = VideoLineParser.getIntValFromText(lines.get(i++));
			} else {
				this.commentCount = 0;
			}
			
			this.timestamp = this.creationDate.getTime();
		} catch ( IndexOutOfBoundsException e) {
			logger.error("IndexOutOfBoundsException for: " + this.tutorId + "/" + this.playlistId + "/" + this.videoId);
		} catch ( ParseException e) {
			logger.error("ParseException for: " + this.tutorId + "/" + this.playlistId + "/" + this.videoId);
		}
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getZaadId() {
		return zaadId;
	}

	public void setZaadId(String zaadId) {
		this.zaadId = zaadId;
	}

	/**
	 * @return the videoId
	 */
	public String getVideoId() {
		return videoId;
	}
	/**
	 * @param videoId the videoId to set
	 */
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	/**
	 * @return the tutorId
	 */
	public String getTutorId() {
		return tutorId;
	}
	/**
	 * @param tutorId the tutorId to set
	 */
	public void setTutorId(String tutorId) {
		this.tutorId = tutorId;
	}
	/**
	 * @return the playlistId
	 */
	public String getPlaylistId() {
		return playlistId;
	}
	/**
	 * @param playlistId the playlistId to set
	 */
	public void setPlaylistId(String playlistId) {
		this.playlistId = playlistId;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the viewCount
	 */
	public int getViewCount() {
		return viewCount;
	}
	/**
	 * @param viewCount the viewCount to set
	 */
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	/**
	 * @return the likeCount
	 */
	public int getLikeCount() {
		return likeCount;
	}
	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	/**
	 * @return the dislikCount
	 */
	public int getDislikCount() {
		return dislikCount;
	}
	/**
	 * @param dislikCount the dislikCount to set
	 */
	public void setDislikCount(int dislikCount) {
		this.dislikCount = dislikCount;
	}

	/**
	 * @return the tutor
	 */
	public Tutor getTutor() {
		return tutor;
	}

	/**
	 * @param tutor the tutor to set
	 */
	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public double getRecommendarity() {
		return recommendarity;
	}

	public void setRecommendarity(double recommendarity) {
		this.recommendarity = recommendarity;
	}
	
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * @return the commentCount
	 */
	public int getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public static void main(String[] args) {
		System.out.println();
	}

	/**
	 * @return the playlist
	 */
	public Playlist getPlaylist() {
		return playlist;
	}

	/**
	 * @param playlist the playlist to set
	 */
	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}
	
	public void calcRanking() {
		this.popularity = ZaadVideoRanking.calcPopularity(this.likeCount, this.dislikCount, this.viewCount, this.playlist.getViewCount(), this.creationDate);
		this.recommendarity = ZaadVideoRanking.calcRecommendarity(this.likeCount, this.dislikCount, this.viewCount, this.commentCount, this.playlist.getViewCount(), this.creationDate);
	}
}
