// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: main/java/com/lili/proto/pb/Ping.proto

package com.lili.proto.java;

public final class PingProto {
  private PingProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface PingMsgOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required int64 clientTimestamp = 1;
    /**
     * <code>required int64 clientTimestamp = 1;</code>
     */
    boolean hasClientTimestamp();
    /**
     * <code>required int64 clientTimestamp = 1;</code>
     */
    long getClientTimestamp();
  }
  /**
   * Protobuf type {@code com.lili.proto.pb.PingMsg}
   */
  public static final class PingMsg extends
      com.google.protobuf.GeneratedMessage
      implements PingMsgOrBuilder {
    // Use PingMsg.newBuilder() to construct.
    private PingMsg(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private PingMsg(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final PingMsg defaultInstance;
    public static PingMsg getDefaultInstance() {
      return defaultInstance;
    }

    public PingMsg getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private PingMsg(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              clientTimestamp_ = input.readInt64();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.lili.proto.java.PingProto.internal_static_com_lili_proto_pb_PingMsg_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.lili.proto.java.PingProto.internal_static_com_lili_proto_pb_PingMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.lili.proto.java.PingProto.PingMsg.class, com.lili.proto.java.PingProto.PingMsg.Builder.class);
    }

    public static com.google.protobuf.Parser<PingMsg> PARSER =
        new com.google.protobuf.AbstractParser<PingMsg>() {
      public PingMsg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PingMsg(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<PingMsg> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required int64 clientTimestamp = 1;
    public static final int CLIENTTIMESTAMP_FIELD_NUMBER = 1;
    private long clientTimestamp_;
    /**
     * <code>required int64 clientTimestamp = 1;</code>
     */
    public boolean hasClientTimestamp() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required int64 clientTimestamp = 1;</code>
     */
    public long getClientTimestamp() {
      return clientTimestamp_;
    }

    private void initFields() {
      clientTimestamp_ = 0L;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasClientTimestamp()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt64(1, clientTimestamp_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, clientTimestamp_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.lili.proto.java.PingProto.PingMsg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.lili.proto.java.PingProto.PingMsg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.lili.proto.java.PingProto.PingMsg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.lili.proto.java.PingProto.PingMsg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.lili.proto.java.PingProto.PingMsg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.lili.proto.java.PingProto.PingMsg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.lili.proto.java.PingProto.PingMsg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.lili.proto.java.PingProto.PingMsg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.lili.proto.java.PingProto.PingMsg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.lili.proto.java.PingProto.PingMsg parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.lili.proto.java.PingProto.PingMsg prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.lili.proto.pb.PingMsg}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.lili.proto.java.PingProto.PingMsgOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.lili.proto.java.PingProto.internal_static_com_lili_proto_pb_PingMsg_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.lili.proto.java.PingProto.internal_static_com_lili_proto_pb_PingMsg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.lili.proto.java.PingProto.PingMsg.class, com.lili.proto.java.PingProto.PingMsg.Builder.class);
      }

      // Construct using com.lili.proto.java.PingProto.PingMsg.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        clientTimestamp_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.lili.proto.java.PingProto.internal_static_com_lili_proto_pb_PingMsg_descriptor;
      }

      public com.lili.proto.java.PingProto.PingMsg getDefaultInstanceForType() {
        return com.lili.proto.java.PingProto.PingMsg.getDefaultInstance();
      }

      public com.lili.proto.java.PingProto.PingMsg build() {
        com.lili.proto.java.PingProto.PingMsg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.lili.proto.java.PingProto.PingMsg buildPartial() {
        com.lili.proto.java.PingProto.PingMsg result = new com.lili.proto.java.PingProto.PingMsg(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.clientTimestamp_ = clientTimestamp_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.lili.proto.java.PingProto.PingMsg) {
          return mergeFrom((com.lili.proto.java.PingProto.PingMsg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.lili.proto.java.PingProto.PingMsg other) {
        if (other == com.lili.proto.java.PingProto.PingMsg.getDefaultInstance()) return this;
        if (other.hasClientTimestamp()) {
          setClientTimestamp(other.getClientTimestamp());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasClientTimestamp()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.lili.proto.java.PingProto.PingMsg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.lili.proto.java.PingProto.PingMsg) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required int64 clientTimestamp = 1;
      private long clientTimestamp_ ;
      /**
       * <code>required int64 clientTimestamp = 1;</code>
       */
      public boolean hasClientTimestamp() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required int64 clientTimestamp = 1;</code>
       */
      public long getClientTimestamp() {
        return clientTimestamp_;
      }
      /**
       * <code>required int64 clientTimestamp = 1;</code>
       */
      public Builder setClientTimestamp(long value) {
        bitField0_ |= 0x00000001;
        clientTimestamp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 clientTimestamp = 1;</code>
       */
      public Builder clearClientTimestamp() {
        bitField0_ = (bitField0_ & ~0x00000001);
        clientTimestamp_ = 0L;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.lili.proto.pb.PingMsg)
    }

    static {
      defaultInstance = new PingMsg(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.lili.proto.pb.PingMsg)
  }

  public interface PongMsgOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required int64 clientTimestamp = 1;
    /**
     * <code>required int64 clientTimestamp = 1;</code>
     */
    boolean hasClientTimestamp();
    /**
     * <code>required int64 clientTimestamp = 1;</code>
     */
    long getClientTimestamp();
  }
  /**
   * Protobuf type {@code com.lili.proto.pb.PongMsg}
   */
  public static final class PongMsg extends
      com.google.protobuf.GeneratedMessage
      implements PongMsgOrBuilder {
    // Use PongMsg.newBuilder() to construct.
    private PongMsg(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private PongMsg(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final PongMsg defaultInstance;
    public static PongMsg getDefaultInstance() {
      return defaultInstance;
    }

    public PongMsg getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private PongMsg(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              clientTimestamp_ = input.readInt64();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.lili.proto.java.PingProto.internal_static_com_lili_proto_pb_PongMsg_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.lili.proto.java.PingProto.internal_static_com_lili_proto_pb_PongMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.lili.proto.java.PingProto.PongMsg.class, com.lili.proto.java.PingProto.PongMsg.Builder.class);
    }

    public static com.google.protobuf.Parser<PongMsg> PARSER =
        new com.google.protobuf.AbstractParser<PongMsg>() {
      public PongMsg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PongMsg(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<PongMsg> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required int64 clientTimestamp = 1;
    public static final int CLIENTTIMESTAMP_FIELD_NUMBER = 1;
    private long clientTimestamp_;
    /**
     * <code>required int64 clientTimestamp = 1;</code>
     */
    public boolean hasClientTimestamp() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required int64 clientTimestamp = 1;</code>
     */
    public long getClientTimestamp() {
      return clientTimestamp_;
    }

    private void initFields() {
      clientTimestamp_ = 0L;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasClientTimestamp()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt64(1, clientTimestamp_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, clientTimestamp_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.lili.proto.java.PingProto.PongMsg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.lili.proto.java.PingProto.PongMsg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.lili.proto.java.PingProto.PongMsg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.lili.proto.java.PingProto.PongMsg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.lili.proto.java.PingProto.PongMsg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.lili.proto.java.PingProto.PongMsg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.lili.proto.java.PingProto.PongMsg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.lili.proto.java.PingProto.PongMsg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.lili.proto.java.PingProto.PongMsg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.lili.proto.java.PingProto.PongMsg parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.lili.proto.java.PingProto.PongMsg prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.lili.proto.pb.PongMsg}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.lili.proto.java.PingProto.PongMsgOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.lili.proto.java.PingProto.internal_static_com_lili_proto_pb_PongMsg_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.lili.proto.java.PingProto.internal_static_com_lili_proto_pb_PongMsg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.lili.proto.java.PingProto.PongMsg.class, com.lili.proto.java.PingProto.PongMsg.Builder.class);
      }

      // Construct using com.lili.proto.java.PingProto.PongMsg.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        clientTimestamp_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.lili.proto.java.PingProto.internal_static_com_lili_proto_pb_PongMsg_descriptor;
      }

      public com.lili.proto.java.PingProto.PongMsg getDefaultInstanceForType() {
        return com.lili.proto.java.PingProto.PongMsg.getDefaultInstance();
      }

      public com.lili.proto.java.PingProto.PongMsg build() {
        com.lili.proto.java.PingProto.PongMsg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.lili.proto.java.PingProto.PongMsg buildPartial() {
        com.lili.proto.java.PingProto.PongMsg result = new com.lili.proto.java.PingProto.PongMsg(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.clientTimestamp_ = clientTimestamp_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.lili.proto.java.PingProto.PongMsg) {
          return mergeFrom((com.lili.proto.java.PingProto.PongMsg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.lili.proto.java.PingProto.PongMsg other) {
        if (other == com.lili.proto.java.PingProto.PongMsg.getDefaultInstance()) return this;
        if (other.hasClientTimestamp()) {
          setClientTimestamp(other.getClientTimestamp());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasClientTimestamp()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.lili.proto.java.PingProto.PongMsg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.lili.proto.java.PingProto.PongMsg) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required int64 clientTimestamp = 1;
      private long clientTimestamp_ ;
      /**
       * <code>required int64 clientTimestamp = 1;</code>
       */
      public boolean hasClientTimestamp() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required int64 clientTimestamp = 1;</code>
       */
      public long getClientTimestamp() {
        return clientTimestamp_;
      }
      /**
       * <code>required int64 clientTimestamp = 1;</code>
       */
      public Builder setClientTimestamp(long value) {
        bitField0_ |= 0x00000001;
        clientTimestamp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 clientTimestamp = 1;</code>
       */
      public Builder clearClientTimestamp() {
        bitField0_ = (bitField0_ & ~0x00000001);
        clientTimestamp_ = 0L;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.lili.proto.pb.PongMsg)
    }

    static {
      defaultInstance = new PongMsg(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.lili.proto.pb.PongMsg)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_lili_proto_pb_PingMsg_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_lili_proto_pb_PingMsg_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_lili_proto_pb_PongMsg_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_lili_proto_pb_PongMsg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n&main/java/com/lili/proto/pb/Ping.proto" +
      "\022\021com.lili.proto.pb\"\"\n\007PingMsg\022\027\n\017client" +
      "Timestamp\030\001 \002(\003\"\"\n\007PongMsg\022\027\n\017clientTime" +
      "stamp\030\001 \002(\003B \n\023com.lili.proto.javaB\tPing" +
      "Proto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_lili_proto_pb_PingMsg_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_lili_proto_pb_PingMsg_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_lili_proto_pb_PingMsg_descriptor,
              new java.lang.String[] { "ClientTimestamp", });
          internal_static_com_lili_proto_pb_PongMsg_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_com_lili_proto_pb_PongMsg_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_lili_proto_pb_PongMsg_descriptor,
              new java.lang.String[] { "ClientTimestamp", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}